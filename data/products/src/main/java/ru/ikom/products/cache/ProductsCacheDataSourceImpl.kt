package ru.ikom.products.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.ikom.database.products.ProductsDao
import ru.ikom.products.ProductData
import ru.ikom.products.toProductDBO
import ru.ikom.products.toProductData
import javax.inject.Inject

class ProductsCacheDataSourceImpl @Inject constructor(
    private val dao: ProductsDao
) : ProductsCacheDataSource {

    override suspend fun fetchProducts(): List<ProductData> {
        return dao.fetchProducts().map { it.toProductData() }
    }

    override fun fetchProductsWithFlow(): Flow<List<ProductData>> =
        dao.fetchProductsWithFlow().map { it.map { it.toProductData() } }

    override suspend fun add(product: ProductData) {
        dao.add(product.toProductDBO())
    }

    override suspend fun delete(product: ProductData) {
        dao.delete(product.toProductDBO())
    }
}