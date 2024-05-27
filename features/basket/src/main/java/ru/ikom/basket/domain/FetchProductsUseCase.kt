package ru.ikom.basket.domain

import kotlinx.coroutines.flow.Flow
import ru.ikom.common.Feature
import javax.inject.Inject

@Feature
class FetchProductsUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    suspend operator fun invoke(): Flow<List<ProductDomain>> = repository.fetchProducts()
}