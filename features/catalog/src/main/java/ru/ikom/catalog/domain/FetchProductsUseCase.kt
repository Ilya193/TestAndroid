package ru.ikom.catalog.domain

import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(): LoadResult<List<ProductDomain>> =
        repository.fetchProducts()
}