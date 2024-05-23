package ru.ikom.products

interface ProductsCloudDataSource {

    suspend fun fetchProducts(): List<ProductData>
}