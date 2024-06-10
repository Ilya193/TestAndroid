package ru.ikom.products

import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import coil.disk.DiskCache
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.ikom.basket.di.BasketDeps
import ru.ikom.basket.presentation.BasketRouter
import ru.ikom.catalog.di.CatalogDeps
import ru.ikom.catalog.presentation.CatalogRouter
import ru.ikom.database.products.ProductsDB
import ru.ikom.database.products.ProductsDao
import ru.ikom.details.di.DetailsDeps
import ru.ikom.details.presentation.DetailsRouter
import ru.ikom.network.products.ProductsService
import ru.ikom.products.cache.ProductsCacheDataSource
import ru.ikom.products.cache.ProductsCacheDataSourceImpl
import ru.ikom.products.cloud.ProductsCloudDataSource
import ru.ikom.products.cloud.ProductsCloudDataSourceImpl
import javax.inject.Scope

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent : CatalogDeps, DetailsDeps, BasketDeps {
    fun inject(app: App)
    fun inject(activity: MainActivity)

    override val detailsRouter: DetailsRouter
    override val basketRouter: BasketRouter
    override val catalogRouter: CatalogRouter

    override val productsCacheDataSource: ProductsCacheDataSource
    override val productsCloudDataSource: ProductsCloudDataSource

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module(includes = [RoutersModule::class, NetworkModule::class, DatabaseModule::class])
class AppModule {

    @Provides
    @AppScope
    fun provideNavigationBase(): Navigation.Base = Navigation.Base()

    @Provides
    @AppScope
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @AppScope
    fun provideMainViewModelFactory(
        navigation: Navigation<LaunchScreenMode>,
    ): MainViewModel.Factory =
        MainViewModel.Factory(navigation)

    @Provides
    @AppScope
    fun provideImageLoader(context: Context): ImageLoader =
        ImageLoader(context.applicationContext).newBuilder()
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.02)
                    .directory(context.applicationContext.cacheDir)
                    .build()
            }
            .build()
}

@Module
class RoutersModule {
    @Provides
    @AppScope
    fun provideAppRouter(navigation: Navigation.Base): Navigation<LaunchScreenMode> = navigation

    @Provides
    @AppScope
    fun provideCatalogRouter(navigation: Navigation.Base): CatalogRouter = navigation

    @Provides
    @AppScope
    fun provideDetailsRouter(navigation: Navigation.Base): DetailsRouter = navigation

    @Provides
    @AppScope
    fun provideBasketRouter(navigation: Navigation.Base): BasketRouter = navigation
}

@Module
class NetworkModule {
    @Provides
    @AppScope
    fun provideProductsService(): ProductsService =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ProductsService::class.java)

    @Provides
    @AppScope
    fun provideProductsCloudDataSource(service: ProductsService): ProductsCloudDataSource =
        ProductsCloudDataSourceImpl(service)
}

@Module
class DatabaseModule {
    @Provides
    @AppScope
    fun provideProductsDatabase(context: Context): ProductsDB =
        Room.databaseBuilder(context.applicationContext, ProductsDB::class.java, "products.db").build()

    @Provides
    @AppScope
    fun provideProductsDao(db: ProductsDB): ProductsDao =
        db.productsDao()

    @Provides
    @AppScope
    fun provideProductsCacheDataSource(dao: ProductsDao): ProductsCacheDataSource =
        ProductsCacheDataSourceImpl(dao)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope