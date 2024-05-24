package ru.ikom.products.cloud

import ru.ikom.products.ProductData

interface ProductsCloudDataSource {

    suspend fun fetchProducts(): List<ProductData>
}