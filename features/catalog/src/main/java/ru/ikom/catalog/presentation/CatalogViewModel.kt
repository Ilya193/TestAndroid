package ru.ikom.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.ikom.catalog.domain.FetchProductsUseCase
import ru.ikom.catalog.domain.LoadResult
import javax.inject.Inject

class CatalogViewModel(
    private val router: CatalogRouter,
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), CatalogRouter {

    private val _uiState = MutableStateFlow(CatalogUiState(loading = true))
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    fun action(event: Event) = viewModelScope.launch(dispatcher) {
        when (event) {
            is Event.Retry -> fetchProducts()
            is Event.OpenDetails -> openDetails(Json.encodeToString(event.product))
        }
    }

    private fun fetchProducts() = viewModelScope.launch(dispatcher) {
        _uiState.value = CatalogUiState(loading = true)
        viewModelScope.launch(dispatcher) {
            when (val result = fetchProductsUseCase()) {
                is LoadResult.Success -> {
                    val products = result.data.map { it.toProductUi() }
                    _uiState.value = CatalogUiState(products = products.toList())
                }
                is LoadResult.Error -> {
                    _uiState.value = CatalogUiState(hasError = result.e.getMessage())
                }
            }
        }
    }

    override fun openDetails(data: String) = router.openDetails(data)

    override fun coup() = router.coup()

    class Factory(
        private val router: CatalogRouter,
        private val fetchProductsUseCase: FetchProductsUseCase,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatalogViewModel(router, fetchProductsUseCase, dispatcher) as T
        }
    }
}

data class CatalogUiState(
    val products: List<ProductUi> = emptyList(),
    val loading: Boolean = false,
    val hasError: Int? = null
)

sealed interface Event {
    class Retry : Event

    @JvmInline
    value class OpenDetails(val product: ProductUi) : Event
}