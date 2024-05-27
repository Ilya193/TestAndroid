package ru.ikom.basket.domain

import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class DeleteProductUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    suspend operator fun invoke(product: ProductDomain) = repository.delete(product)
}