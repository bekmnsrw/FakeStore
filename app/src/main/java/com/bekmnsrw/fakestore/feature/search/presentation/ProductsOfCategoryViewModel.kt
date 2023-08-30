package com.bekmnsrw.fakestore.feature.search.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bekmnsrw.fakestore.feature.search.data.ProductsOfCategoryPagingSource
import com.bekmnsrw.fakestore.feature.search.domain.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsOfCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val category = savedStateHandle.get<String>("category") ?: ""

    val pagedProducts = Pager(PagingConfig(ProductsOfCategoryPagingSource.PAGE_SIZE)) {
        ProductsOfCategoryPagingSource(
            categoryRepository = categoryRepository,
            category = category
        )
    }.flow.cachedIn(viewModelScope)

    @Immutable
    data class ProductsOfCategoryScreenState(
        val isLoading: Boolean = false
    )

    @Immutable
    sealed interface ProductsOfCategoryScreenEvent {
        data class OnProductClicked(val id: Long) : ProductsOfCategoryScreenEvent
        object OnArrowBackClicked : ProductsOfCategoryScreenEvent
    }

    @Immutable
    sealed interface ProductsOfCategoryScreenAction {
        data class NavigateProductDetails(val id: Long) : ProductsOfCategoryScreenAction
        object NavigateBack : ProductsOfCategoryScreenAction
    }

    private val _screenState = MutableStateFlow(ProductsOfCategoryScreenState())
    val screenState: StateFlow<ProductsOfCategoryScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<ProductsOfCategoryScreenAction?>()
    val screenAction: SharedFlow<ProductsOfCategoryScreenAction?> = _screenAction.asSharedFlow()

    fun eventHandler(event: ProductsOfCategoryScreenEvent) {
        when (event) {
            is ProductsOfCategoryScreenEvent.OnProductClicked -> onProductClicked(event.id)
            ProductsOfCategoryScreenEvent.OnArrowBackClicked -> onArrowBackClicked()
        }
    }

    private fun onProductClicked(productId: Long) = viewModelScope.launch {
        _screenAction.emit(
            ProductsOfCategoryScreenAction.NavigateProductDetails(id = productId)
        )
    }

    private fun onArrowBackClicked() = viewModelScope.launch {
        _screenAction.emit(
            ProductsOfCategoryScreenAction.NavigateBack
        )
    }
}
