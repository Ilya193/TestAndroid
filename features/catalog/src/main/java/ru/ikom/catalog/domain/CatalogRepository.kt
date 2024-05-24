package ru.ikom.catalog.domain

import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    suspend fun fetchProducts(): LoadResult<List<ProductDomain>>
    suspend fun fetchProductsFromCache(): List<ProductDomain>
    fun fetchProductsFromCacheWithFlow(): Flow<List<ProductDomain>>
    suspend fun add(product: ProductDomain)
    suspend fun delete(product: ProductDomain)
}