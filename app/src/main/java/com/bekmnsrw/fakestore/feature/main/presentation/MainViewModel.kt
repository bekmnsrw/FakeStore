package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import com.bekmnsrw.fakestore.feature.main.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
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
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    init {
        loadProducts()
    }

    @Immutable
    data class MainScreenState(
        val products: PersistentList<ProductMain> = persistentListOf()
    )

    @Immutable
    sealed interface MainScreenEvent {
        data class OnProductClicked(val id: Long) : MainScreenEvent
    }

    @Immutable
    sealed interface MainScreenAction {
        data class NavigateProductDetails(val id: Long) : MainScreenAction
    }

    private val _screenState = MutableStateFlow(MainScreenState())
    val screenState: StateFlow<MainScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<MainScreenAction?>()
    val screenAction: SharedFlow<MainScreenAction?> = _screenAction.asSharedFlow()

    fun eventHandler(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnProductClicked -> onProductClicked()
        }
    }

    private fun loadProducts() = viewModelScope.launch {
        getAllProductsUseCase()
            .flowOn(Dispatchers.IO)
            .collect {
                _screenState.emit(
                    _screenState.value.copy(
                        products = it.toPersistentList()
                    )
                )
            }
    }

    private fun onProductClicked() {
        TODO("Not yet implemented")
    }
}
