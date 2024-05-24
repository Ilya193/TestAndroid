package ru.ikom.basket.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    suspend operator fun invoke(): Flow<List<ProductDomain>> = repository.fetchProducts()
}