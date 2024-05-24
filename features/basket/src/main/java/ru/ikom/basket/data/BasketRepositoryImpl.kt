package ru.ikom.basket.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ikom.basket.domain.BasketRepository
import ru.ikom.basket.domain.ProductDomain
import ru.ikom.products.cache.ProductsCacheDataSource

class BasketRepositoryImpl(
    private val cacheDataSource: ProductsCacheDataSource
) : BasketRepository {
    override fun fetchProducts(): Flow<List<ProductDomain>> {
        return cacheDataSource.fetchProductsWithFlow().map { it.map { it.toProductDomain() } }
    }

    override suspend fun delete(product: ProductDomain) {
        cacheDataSource.delete(product.toProductData())
    }
}