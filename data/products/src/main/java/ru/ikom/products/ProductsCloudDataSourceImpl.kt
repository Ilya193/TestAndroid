package ru.ikom.products

import ru.ikom.network.products.ProductsService
import javax.inject.Inject

class ProductsCloudDataSourceImpl @Inject constructor(
    private val service: ProductsService
) : ProductsCloudDataSource {
    override suspend fun fetchProducts(): List<ProductData> =
        service.fetchProducts().toProductsData()
}