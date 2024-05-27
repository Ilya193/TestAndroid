package ru.ikom.basket.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.ikom.basket.data.BasketRepositoryImpl
import ru.ikom.basket.domain.BasketRepository
import ru.ikom.basket.domain.DeleteProductUseCase
import ru.ikom.basket.domain.FetchProductsUseCase
import ru.ikom.basket.presentation.BasketFragment
import ru.ikom.basket.presentation.BasketRouter
import ru.ikom.basket.presentation.BasketViewModel
import ru.ikom.common.Feature
import ru.ikom.products.cache.ProductsCacheDataSource
import ru.ikom.products.cache.ProductsCacheDataSourceImpl
import kotlin.properties.Delegates

@Component(modules = [BasketModule::class], dependencies = [BasketDeps::class])
@Feature
interface BasketComponent {
    fun inject(fragment: BasketFragment)

    @Component.Builder
    interface Builder {
        fun deps(deps: BasketDeps): Builder

        fun build(): BasketComponent
    }
}

@Module
class BasketModule {
    @Provides
    @Feature
    fun provideBasketRepository(
        cacheDataSource: ProductsCacheDataSource
    ): BasketRepository =
        BasketRepositoryImpl(cacheDataSource)

    @Provides
    @Feature
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

    @Provides
    @Feature
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

interface BasketDeps {
    val basketRouter: BasketRouter
    val productsCacheDataSource: ProductsCacheDataSource
}

interface BasketDepsProvider {
    var deps: BasketDeps

    companion object : BasketDepsProvider by BasketDepsImpl
}

object BasketDepsImpl : BasketDepsProvider {
    override var deps: BasketDeps by Delegates.notNull()
}

class BasketComponentViewModel : ViewModel() {
    val basketComponent = DaggerBasketComponent.builder().deps(BasketDepsProvider.deps).build()
}