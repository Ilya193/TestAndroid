package ru.ikom.catalog.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
import ru.ikom.common.Feature
import ru.ikom.products.cache.ProductsCacheDataSource
import ru.ikom.products.cloud.ProductsCloudDataSource
import kotlin.properties.Delegates

@Component(modules = [CatalogModule::class], dependencies = [CatalogDeps::class])
@Feature
interface CatalogComponent {
    fun inject(fragment: CatalogFragment)

    @Component.Builder
    interface Builder {
        fun deps(deps: CatalogDeps): Builder

        fun build(): CatalogComponent
    }
}

@Module
class CatalogModule {
    @Provides
    @Feature
    fun provideCatalogRepository(
        cloudDataSource: ProductsCloudDataSource,
        cacheDataSource: ProductsCacheDataSource
    ): CatalogRepository =
        CatalogRepositoryImpl(cloudDataSource, cacheDataSource)

    @Provides
    @Feature
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

    @Provides
    @Feature
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

interface CatalogDeps {
    val catalogRouter: CatalogRouter
    val productsCloudDataSource: ProductsCloudDataSource
    val productsCacheDataSource: ProductsCacheDataSource
}

interface CatalogDepsProvider {
    var deps: CatalogDeps

    companion object : CatalogDepsProvider by CatalogDepsImpl
}

object CatalogDepsImpl : CatalogDepsProvider {
    override var deps: CatalogDeps by Delegates.notNull()
}

class CatalogComponentViewModel : ViewModel() {
    val catalogComponent = DaggerCatalogComponent.builder().deps(CatalogDepsProvider.deps).build()
}