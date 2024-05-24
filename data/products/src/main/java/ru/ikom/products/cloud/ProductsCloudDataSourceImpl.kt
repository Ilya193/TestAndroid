package ru.ikom.products.cloud

import ru.ikom.network.products.ProductsService
import ru.ikom.products.ProductData
import ru.ikom.products.toProductsData
import javax.inject.Inject

class ProductsCloudDataSourceImpl @Inject constructor(
    private val service: ProductsService
) : ProductsCloudDataSource {
    override suspend fun fetchProducts(): List<ProductData> =
        service.fetchProducts().toProductsData()
}