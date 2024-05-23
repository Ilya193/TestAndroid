package ru.ikom.network.products.model

data class ProductsDTO(
    val limit: Int,
    val products: List<ProductDTO>,
    val skip: Int,
    val total: Int
)