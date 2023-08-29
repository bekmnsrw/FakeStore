package com.bekmnsrw.fakestore.feature.search.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekmnsrw.fakestore.feature.search.domain.usecase.GetAllCategoriesUseCase
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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    @Immutable
    data class CategoryScreenState(
        val isLoading: Boolean = false,
        val categories: PersistentList<String> = persistentListOf()
    )

    @Immutable
    sealed interface CategoryScreenEvent {}

    @Immutable
    sealed interface CategoryScreenAction {}

    private val _screenState = MutableStateFlow(CategoryScreenState())
    val screenState: StateFlow<CategoryScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<CategoryScreenAction?>()
    val screenAction: SharedFlow<CategoryScreenAction?> = _screenAction.asSharedFlow()

    fun eventHandler(event: CategoryScreenEvent) {
        when (event) {
            else -> {}
        }
    }

    init {
        loadAllCategories()
    }

    private fun loadAllCategories() = viewModelScope.launch {
        getAllCategoriesUseCase()
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
                        categories = it.toPersistentList()
                    )
                )
            }
    }
}
