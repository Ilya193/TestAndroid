package ru.ikom.catalog.domain

import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(product: ProductDomain) = repository.add(product)
}