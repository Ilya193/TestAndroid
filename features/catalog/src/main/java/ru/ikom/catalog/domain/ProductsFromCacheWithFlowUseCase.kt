package ru.ikom.catalog.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsFromCacheWithFlowUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    operator fun invoke(): Flow<List<ProductDomain>> =
        repository.fetchProductsFromCacheWithFlow()

    suspend fun test(): List<ProductDomain> =
        repository.fetchProductsFromCache()
}