package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.bekmnsrw.fakestore.feature.main.data.ProductPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productPagingSource: ProductPagingSource
) : ViewModel() {

    val pagedProducts = Pager(PagingConfig(ProductPagingSource.PAGE_SIZE)) { productPagingSource }.flow

    @Immutable
    data class MainScreenState(
        val isLoading: Boolean = false
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
            is MainScreenEvent.OnProductClicked -> onProductClicked(event.id)
        }
    }

    private fun onProductClicked(productId: Long) = viewModelScope.launch {
        _screenAction.emit(
            MainScreenAction.NavigateProductDetails(
                id = productId
            )
        )
    }
}
