package ru.ikom.catalog.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.ikom.catalog.data.CatalogRepositoryImpl
import ru.ikom.catalog.domain.CatalogRepository
import ru.ikom.catalog.domain.FetchProductsUseCase
import ru.ikom.catalog.presentation.CatalogFragment
import ru.ikom.catalog.presentation.CatalogRouter
import ru.ikom.catalog.presentation.CatalogViewModel
import ru.ikom.products.ProductsCloudDataSource
import ru.ikom.products.ProductsCloudDataSourceImpl

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
    fun provideCatalogRepository(cloudDataSource: ProductsCloudDataSource): CatalogRepository =
        CatalogRepositoryImpl(cloudDataSource)

    @Provides
    fun provideCatalogViewModelFactory(
        router: CatalogRouter,
        fetchProductsUseCase: FetchProductsUseCase,
        dispatcher: CoroutineDispatcher
    ): CatalogViewModel.Factory =
        CatalogViewModel.Factory(router, fetchProductsUseCase, dispatcher)
}

@Module
interface BindsCatalogModule {
    @Binds
    fun bindsProductsCloudDataSource(cloudDataSource: ProductsCloudDataSourceImpl): ProductsCloudDataSource
}

interface CatalogComponentProvider {
    fun provideCatalogComponent(): CatalogComponent
}