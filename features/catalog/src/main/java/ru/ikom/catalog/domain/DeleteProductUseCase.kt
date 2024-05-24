package ru.ikom.catalog.domain

import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(product: ProductDomain) = repository.delete(product)
}