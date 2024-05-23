package ru.ikom.catalog.data

import ru.ikom.catalog.domain.CatalogRepository
import ru.ikom.catalog.domain.LoadResult
import ru.ikom.catalog.domain.ProductDomain
import ru.ikom.catalog.domain.ErrorType
import ru.ikom.products.ProductsCloudDataSource
import java.net.UnknownHostException
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val cloudDataSource: ProductsCloudDataSource
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
}