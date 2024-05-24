package ru.ikom.basket.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.ikom.basket.data.BasketRepositoryImpl
import ru.ikom.basket.domain.BasketRepository
import ru.ikom.basket.domain.DeleteProductUseCase
import ru.ikom.basket.domain.FetchProductsUseCase
import ru.ikom.basket.presentation.BasketFragment
import ru.ikom.basket.presentation.BasketRouter
import ru.ikom.basket.presentation.BasketViewModel
import ru.ikom.products.cache.ProductsCacheDataSource
import ru.ikom.products.cache.ProductsCacheDataSourceImpl

@Subcomponent(modules = [BindsBasketModule::class, BasketModule::class])
interface BasketComponent {
    fun inject(fragment: BasketFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BasketComponent
    }
}

@Module
class BasketModule {
    @Provides
    fun provideBasketRepository(
        cacheDataSource: ProductsCacheDataSource
    ): BasketRepository =
        BasketRepositoryImpl(cacheDataSource)

    @Provides
    fun provideBasketViewModelFactory(
        router: BasketRouter,
        fetchProductsUseCase: FetchProductsUseCase,
        deleteProductUseCase: DeleteProductUseCase,
        dispatcher: CoroutineDispatcher
    ): BasketViewModel.Factory =
        BasketViewModel.Factory(
            router,
            fetchProductsUseCase,
            deleteProductUseCase,
            dispatcher
        )
}

@Module
interface BindsBasketModule {
    @Binds
    fun bindsProductsCacheDataSource(cacheDataSource: ProductsCacheDataSourceImpl): ProductsCacheDataSource
}

interface BasketComponentProvider {
    fun provideBasketComponent(): BasketComponent
}