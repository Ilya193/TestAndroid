package ru.ikom.catalog.domain

import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class FetchProductsUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(): LoadResult<List<ProductDomain>> =
        repository.fetchProducts()
}