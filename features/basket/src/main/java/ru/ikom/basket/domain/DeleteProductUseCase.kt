package ru.ikom.basket.domain

import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    suspend operator fun invoke(product: ProductDomain) = repository.delete(product)
}