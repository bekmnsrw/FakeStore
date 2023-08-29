package com.bekmnsrw.fakestore.feature.main.presentation.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import com.bekmnsrw.fakestore.feature.main.domain.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val PRODUCT_ID_KEY = "productId"
    }

    init {
        savedStateHandle.get<String>(PRODUCT_ID_KEY)?.let {
            loadProductById(it.toLong())
        }
    }

    @Immutable
    data class DetailsScreenState(
        val productDetails: ProductDetails? = null,
        val isLoading: Boolean = false,
        val isFavorite: Boolean = false
    )

    @Immutable
    sealed interface DetailsScreenEvent {
        object OnArrowBackClicked : DetailsScreenEvent
        data class OnFavoriteClicked(val id: Long) : DetailsScreenEvent
    }

    @Immutable
    sealed interface DetailsScreenAction {
        object NavigateBack : DetailsScreenAction
    }

    private val _screenState = MutableStateFlow(DetailsScreenState())
    val screenState: StateFlow<DetailsScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<DetailsScreenAction?>()
    val screenAction: SharedFlow<DetailsScreenAction?> = _screenAction.asSharedFlow()

    fun eventHandler(event: DetailsScreenEvent) {
        when (event) {
            DetailsScreenEvent.OnArrowBackClicked -> onArrowBackClicked()
            is DetailsScreenEvent.OnFavoriteClicked -> onFavoriteClicked(event.id)
        }
    }

    private fun loadProductById(id: Long) = viewModelScope.launch {
        getProductByIdUseCase(id)
            .flowOn(Dispatchers.IO)
            .onStart {
                _screenState.emit(
                    _screenState.value.copy(
                        isLoading = true
                    )
                )
            }
            .onCompletion {
                _screenState.emit(
                    _screenState.value.copy(
                        isLoading = false
                    )
                )
            }
            .collect {
                _screenState.emit(
                    _screenState.value.copy(
                        productDetails = it
                    )
                )
            }
    }

    private fun onArrowBackClicked() = viewModelScope.launch {
        _screenAction.emit(DetailsScreenAction.NavigateBack)
    }

    private fun onFavoriteClicked(id: Long) = viewModelScope.launch {
        // TODO: API Request
        println("favorite id: $id")
        _screenState.emit(
            _screenState.value.copy(
                isFavorite = !_screenState.value.isFavorite
            )
        )
    }
}
