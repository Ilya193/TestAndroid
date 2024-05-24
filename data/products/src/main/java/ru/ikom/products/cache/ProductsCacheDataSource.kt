package ru.ikom.products.cache

import kotlinx.coroutines.flow.Flow
import ru.ikom.products.ProductData

interface ProductsCacheDataSource {
    suspend fun fetchProducts(): List<ProductData>
    fun fetchProductsWithFlow(): Flow<List<ProductData>>
    suspend fun add(product: ProductData)
    suspend fun delete(product: ProductData)
}