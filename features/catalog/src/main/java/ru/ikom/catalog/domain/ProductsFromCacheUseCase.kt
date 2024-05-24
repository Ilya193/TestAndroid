package ru.ikom.catalog.domain

import javax.inject.Inject

class ProductsFromCacheUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(): List<ProductDomain> =
        repository.fetchProductsFromCache()
}