package ru.ikom.catalog.domain

interface CatalogRepository {
    suspend fun fetchProducts(): LoadResult<List<ProductDomain>>
}