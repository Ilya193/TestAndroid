package ru.ikom.catalog.domain

import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class DeleteProductUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(product: ProductDomain) = repository.delete(product)
}