package ru.ikom.products

import ru.ikom.network.products.ProductsService

class ProductsCloudDataSourceImpl(
    private val service: ProductsService
) : ProductsCloudDataSource {
    override suspend fun fetchProducts(): List<ProductData> =
        service.fetchProducts().toProductsData()
}