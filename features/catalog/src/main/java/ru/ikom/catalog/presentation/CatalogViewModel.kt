package ru.ikom.catalog.presentation

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
import ru.ikom.catalog.domain.AddProductUseCase
import ru.ikom.catalog.domain.DeleteProductUseCase
import ru.ikom.catalog.domain.FetchProductsUseCase
import ru.ikom.catalog.domain.LoadResult
import ru.ikom.catalog.domain.ProductsFromCacheUseCase
import ru.ikom.catalog.domain.ProductsFromCacheWithFlowUseCase

class CatalogViewModel(
    private val router: CatalogRouter,
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val productsFromCacheWithFlowUseCase: ProductsFromCacheWithFlowUseCase,
    private val productsFromCacheUseCase: ProductsFromCacheUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), CatalogRouter {

    private var initProducts = mutableListOf<ProductUi>()
    private var products = mutableListOf<ProductUi>()
    private val _uiState = MutableStateFlow(CatalogUiState(loading = true))
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            fetchProducts()
            productsFromCacheWithFlowUseCase().map { it.map { it.toProductUi() } }
                .collect { productsCache ->
                    compare(products, productsCache)
                }
        }
    }

    private fun compare(cloud: List<ProductUi>, cache: List<ProductUi>) {
        if (cloud.isNotEmpty()) {
            products = if (cache.isEmpty()) initProducts else {
                val temp = mutableListOf<ProductUi>()
                cloud.forEach {
                    val added = cache.any { cache -> cache.id == it.id }
                    temp.add(it.copy(added = added))
                }
                temp
            }
            _uiState.value = CatalogUiState(products = products.toList())
        }
    }

    fun action(event: Event) = viewModelScope.launch(dispatcher) {
        when (event) {
            is Event.Retry -> fetchProducts()
            is Event.OpenDetails -> openDetails(Json.encodeToString(event.product))
            is Event.AddProduct -> addProduct(event.product)
            is Event.OpenBasket -> openBasket()
        }
    }

    private fun fetchProducts() = viewModelScope.launch(dispatcher) {
        _uiState.value = CatalogUiState(loading = true)
        viewModelScope.launch(dispatcher) {
            when (val result = fetchProductsUseCase()) {
                is LoadResult.Success -> {
                    products = result.data.map { it.toProductUi() }.toMutableList()
                    initProducts = products.toMutableList()
                    val cache = productsFromCacheUseCase().map { it.toProductUi() }
                    compare(products, cache)
                    _uiState.value = CatalogUiState(products = products.toList())
                }

                is LoadResult.Error -> {
                    _uiState.value = CatalogUiState(hasError = result.e.getMessage())
                }
            }
        }
    }

    private fun addProduct(product: ProductUi) = viewModelScope.launch(dispatcher) {
        if (product.added) deleteProductUseCase(product.toProductDomain())
        else addProductUseCase(product.toProductDomain())
        _uiState.value = CatalogUiState(products = products.toList())
    }

    override fun openDetails(data: String) = router.openDetails(data)

    override fun openBasket() = router.openBasket()

    override fun coup() = router.coup()

    class Factory(
        private val router: CatalogRouter,
        private val fetchProductsUseCase: FetchProductsUseCase,
        private val productsFromCacheWithFlowUseCase: ProductsFromCacheWithFlowUseCase,
        private val productsFromCacheUseCase: ProductsFromCacheUseCase,
        private val addProductUseCase: AddProductUseCase,
        private val deleteProductUseCase: DeleteProductUseCase,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatalogViewModel(
                router,
                fetchProductsUseCase,
                productsFromCacheWithFlowUseCase,
                productsFromCacheUseCase,
                addProductUseCase,
                deleteProductUseCase,
                dispatcher
            ) as T
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

    class AddProduct(val product: ProductUi) : Event

    @JvmInline
    value class OpenDetails(val product: ProductUi) : Event

    class OpenBasket : Event

}