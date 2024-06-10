package ru.ikom.catalog.domain

import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class ProductsFromCacheUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(): List<ProductDomain> =
        repository.fetchProductsFromCache()
}