package com.bekmnsrw.fakestore.feature.main.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
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
) : ViewModel() {

    companion object {
        private const val PRODUCT_ID_KEY = "productId"
    }

    @Immutable
    data class DetailsScreenState(
        val productDetails: ProductDetails? = null,
        val isLoading: Boolean = false
    )

    @Immutable
    sealed interface DetailsScreenEvent {

    }

    @Immutable
    sealed interface DetailsScreenAction {

    }

    private val _screenState = MutableStateFlow(DetailsScreenState())
    val screenState: StateFlow<DetailsScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<DetailsScreenAction?>()
    val screenAction: SharedFlow<DetailsScreenAction?> = _screenAction.asSharedFlow()

    fun eventHandler(event: DetailsScreenEvent) {
        when (event) {
            else -> {}
        }
    }
}
