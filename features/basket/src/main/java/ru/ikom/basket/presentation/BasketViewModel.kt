package ru.ikom.basket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.ikom.basket.domain.DeleteProductUseCase
import ru.ikom.basket.domain.FetchProductsUseCase

class BasketViewModel(
    private val router: BasketRouter,
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), BasketRouter {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            fetchProductsUseCase().map { it.map { it.toProductUi() } }
                .collect {
                    _uiState.value = CatalogUiState(it, it.isEmpty())
                }
        }
    }

    fun action(event: Event) = viewModelScope.launch(dispatcher) {
        when (event) {
            is Event.DeleteProduct -> deleteProduct(event.product)
            is Event.OpenDetails -> openDetails(Json.encodeToString(event.product))
        }
    }

    private fun deleteProduct(product: ProductUi) = viewModelScope.launch(dispatcher) {
        deleteProductUseCase(product.toProductDomain())
    }

    override fun openDetails(data: String) = router.openDetails(data)

    override fun coup() = router.coup()

    class Factory(
        private val router: BasketRouter,
        private val fetchProductsUseCase: FetchProductsUseCase,
        private val deleteProductUseCase: DeleteProductUseCase,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BasketViewModel(
                router,
                fetchProductsUseCase,
                deleteProductUseCase,
                dispatcher
            ) as T
        }
    }
}

data class CatalogUiState(
    val products: List<ProductUi> = emptyList(),
    val isEmpty: Boolean = true
)

sealed interface Event {
    @JvmInline
    value class DeleteProduct(val product: ProductUi) : Event

    @JvmInline
    value class OpenDetails(val product: ProductUi) : Event
}