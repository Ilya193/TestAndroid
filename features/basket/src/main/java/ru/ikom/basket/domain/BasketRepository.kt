package ru.ikom.basket.domain

import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun fetchProducts(): Flow<List<ProductDomain>>
    suspend fun delete(product: ProductDomain)
}