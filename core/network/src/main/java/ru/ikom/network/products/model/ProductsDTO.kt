package ru.ikom.network.products.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDTO(
    val limit: Int,
    val products: List<ProductDTO>,
    val skip: Int,
    val total: Int
)