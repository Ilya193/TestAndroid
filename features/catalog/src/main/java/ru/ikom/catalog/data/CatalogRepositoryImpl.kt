package ru.ikom.catalog.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ikom.catalog.domain.CatalogRepository
import ru.ikom.catalog.domain.LoadResult
import ru.ikom.catalog.domain.ProductDomain
import ru.ikom.catalog.domain.ErrorType
import ru.ikom.products.cache.ProductsCacheDataSource
import ru.ikom.products.cloud.ProductsCloudDataSource
import java.net.UnknownHostException
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val cloudDataSource: ProductsCloudDataSource,
    private val cacheDataSource: ProductsCacheDataSource
) : CatalogRepository {
    override suspend fun fetchProducts(): LoadResult<List<ProductDomain>> {
        return try {
            val products = cloudDataSource.fetchProducts().map { it.toProductDomain() }
            LoadResult.Success(products)
        } catch (e: UnknownHostException) {
            LoadResult.Error(ErrorType.NO_CONNECTION)
        } catch (e: Exception) {
            LoadResult.Error(ErrorType.GENERIC_ERROR)
        }
    }

    override suspend fun fetchProductsFromCache(): List<ProductDomain> {
        return cacheDataSource.fetchProducts().map { it.toProductDomain() }
    }

    override fun fetchProductsFromCacheWithFlow(): Flow<List<ProductDomain>> {
        return cacheDataSource.fetchProductsWithFlow().map { it.map { it.toProductDomain() } }
    }

    override suspend fun add(product: ProductDomain) {
        cacheDataSource.add(product.toProductData())
    }

    override suspend fun delete(product: ProductDomain) {
        cacheDataSource.delete(product.toProductData())
    }
}