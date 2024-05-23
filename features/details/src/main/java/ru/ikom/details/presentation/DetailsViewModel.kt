package ru.ikom.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class DetailsViewModel(
    private val router: DetailsRouter,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), DetailsRouter {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun action(event: Event) = viewModelScope.launch(dispatcher) {
        when(event) {
            is Event.Init -> init(event.data)
        }
    }

    private fun init(data: String) {
        try {
            _uiState.value = DetailsUiState(product = Json.decodeFromString(data))
        } catch (e: Exception) {
            _uiState.value = DetailsUiState(hasError = true)
        }
    }

    override fun coup() = router.coup()

    class Factory(
        private val router: DetailsRouter,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(router, dispatcher) as T
        }
    }
}

data class DetailsUiState(
    val product: ProductUi? = null,
    val hasError: Boolean = false
)

sealed interface Event {
    @JvmInline
    value class Init(val data: String) : Event
}