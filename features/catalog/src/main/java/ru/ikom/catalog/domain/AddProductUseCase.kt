package ru.ikom.catalog.domain

import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class AddProductUseCase @Inject constructor(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(product: ProductDomain) = repository.add(product)
}