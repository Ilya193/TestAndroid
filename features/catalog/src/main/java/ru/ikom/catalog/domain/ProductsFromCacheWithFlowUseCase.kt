package ru.ikom.catalog.domain

import kotlinx.coroutines.flow.Flow
import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class ProductsFromCacheWithFlowUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    operator fun invoke(): Flow<List<ProductDomain>> =
        repository.fetchProductsFromCacheWithFlow()

    suspend fun test(): List<ProductDomain> =
        repository.fetchProductsFromCache()
}