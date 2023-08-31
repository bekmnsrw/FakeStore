package com.bekmnsrw.fakestore.feature.main.presentation.list

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bekmnsrw.fakestore.feature.main.data.ProductPagingSource
import com.bekmnsrw.fakestore.feature.search.domain.usecase.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productPagingSource: ProductPagingSource,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    val pagedProducts = Pager(PagingConfig(ProductPagingSource.PAGE_SIZE)) {
        productPagingSource
    }.flow.cachedIn(viewModelScope)

    @Immutable
    data class MainScreenState(
        val categories: List<String> = persistentListOf()
    )

    @Immutable
    sealed interface MainScreenEvent {
        data class OnProductClicked(val id: Long) : MainScreenEvent
        data class OnCategoryClicked(val category: String) : MainScreenEvent
    }

    @Immutable
    sealed interface MainScreenAction {
        data class NavigateProductDetails(val id: Long) : MainScreenAction
        data class NavigateProductsOfCategoryList(val category: String) : MainScreenAction
    }

    private val _screenState = MutableStateFlow(MainScreenState())
    val screenState: StateFlow<MainScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<MainScreenAction?>()
    val screenAction: SharedFlow<MainScreenAction?> = _screenAction.asSharedFlow()

    fun eventHandler(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnProductClicked -> onProductClicked(event.id)
            is MainScreenEvent.OnCategoryClicked -> onCategoryClicked(event.category)
        }
    }

    init {
        loadAllCategories()
    }

    private fun onProductClicked(productId: Long) = viewModelScope.launch {
        _screenAction.emit(
            MainScreenAction.NavigateProductDetails(
                id = productId
            )
        )
    }

    private fun loadAllCategories() = viewModelScope.launch {
        getAllCategoriesUseCase()
            .flowOn(Dispatchers.IO)
            .collect {
                _screenState.emit(
                    _screenState.value.copy(
                        categories = it.toPersistentList()
                    )
                )
            }
    }

    private fun onCategoryClicked(category: String) = viewModelScope.launch {
        _screenAction.emit(
            MainScreenAction.NavigateProductsOfCategoryList(
                category = category
            )
        )
    }
}
