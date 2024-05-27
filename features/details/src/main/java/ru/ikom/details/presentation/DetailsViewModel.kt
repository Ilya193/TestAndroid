package ru.ikom.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.ikom.common.Feature

class DetailsViewModel(
    private val data: String,
    private val router: DetailsRouter,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), DetailsRouter {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            try {
                _uiState.value = DetailsUiState(product = Json.decodeFromString(data))
            } catch (e: Exception) {
                _uiState.value = DetailsUiState(hasError = true)
            }
        }
    }

    override fun coup() = router.coup()

    class Factory @AssistedInject constructor(
        @Assisted private val data: String,
        private val router: DetailsRouter,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(data, router, dispatcher) as T
        }

        @AssistedFactory
        @Feature
        interface Factory {
            fun create(@Assisted data: String): DetailsViewModel.Factory
        }
    }
}

data class DetailsUiState(
    val product: ProductUi? = null,
    val hasError: Boolean = false
)