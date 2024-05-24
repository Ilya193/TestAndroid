package ru.ikom.catalog.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.ikom.catalog.data.CatalogRepositoryImpl
import ru.ikom.catalog.domain.AddProductUseCase
import ru.ikom.catalog.domain.CatalogRepository
import ru.ikom.catalog.domain.DeleteProductUseCase
import ru.ikom.catalog.domain.FetchProductsUseCase
import ru.ikom.catalog.domain.ProductsFromCacheUseCase
import ru.ikom.catalog.domain.ProductsFromCacheWithFlowUseCase
import ru.ikom.catalog.presentation.CatalogFragment
import ru.ikom.catalog.presentation.CatalogRouter
import ru.ikom.catalog.presentation.CatalogViewModel
import ru.ikom.products.cache.ProductsCacheDataSource
import ru.ikom.products.cache.ProductsCacheDataSourceImpl
import ru.ikom.products.cloud.ProductsCloudDataSource
import ru.ikom.products.cloud.ProductsCloudDataSourceImpl

@Subcomponent(modules = [BindsCatalogModule::class, CatalogModule::class])
interface CatalogComponent {
    fun inject(fragment: CatalogFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CatalogComponent
    }
}

@Module
class CatalogModule {
    @Provides
    fun provideCatalogRepository(
        cloudDataSource: ProductsCloudDataSource,
        cacheDataSource: ProductsCacheDataSource
    ): CatalogRepository =
        CatalogRepositoryImpl(cloudDataSource, cacheDataSource)

    @Provides
    fun provideCatalogViewModelFactory(
        router: CatalogRouter,
        fetchProductsUseCase: FetchProductsUseCase,
        productsFromCacheWithFlowUseCase: ProductsFromCacheWithFlowUseCase,
        productsFromCacheUseCase: ProductsFromCacheUseCase,
        addProductUseCase: AddProductUseCase,
        deleteProductUseCase: DeleteProductUseCase,
        dispatcher: CoroutineDispatcher
    ): CatalogViewModel.Factory =
        CatalogViewModel.Factory(
            router,
            fetchProductsUseCase,
            productsFromCacheWithFlowUseCase,
            productsFromCacheUseCase,
            addProductUseCase,
            deleteProductUseCase,
            dispatcher
        )
}

@Module
interface BindsCatalogModule {
    @Binds
    fun bindsProductsCloudDataSource(cloudDataSource: ProductsCloudDataSourceImpl): ProductsCloudDataSource

    @Binds
    fun bindsProductsCacheDataSource(cacheDataSource: ProductsCacheDataSourceImpl): ProductsCacheDataSource
}

interface CatalogComponentProvider {
    fun provideCatalogComponent(): CatalogComponent
}