package ru.ikom.products

import android.content.Context
import androidx.room.Room
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
import ru.ikom.basket.di.BasketComponent
import ru.ikom.basket.presentation.BasketRouter
import ru.ikom.catalog.di.CatalogComponent
import ru.ikom.catalog.presentation.CatalogRouter
import ru.ikom.database.products.ProductsDB
import ru.ikom.database.products.ProductsDao
import ru.ikom.details.di.DetailsComponent
import ru.ikom.details.presentation.DetailsRouter
import ru.ikom.network.products.ProductsService
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppSubcomponents::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun catalogComponent(): CatalogComponent.Factory

    fun detailsComponent(): DetailsComponent.Factory

    fun basketComponent(): BasketComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module(subcomponents = [CatalogComponent::class, DetailsComponent::class, BasketComponent::class])
class AppSubcomponents

@Module(includes = [RoutersModule::class, NetworkModule::class, DatabaseModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideNavigationBase(): Navigation.Base = Navigation.Base()

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideMainViewModelFactory(
        navigation: Navigation<LaunchScreenMode>,
    ): MainViewModel.Factory =
        MainViewModel.Factory(navigation)
}

@Module
class RoutersModule {
    @Singleton
    @Provides
    fun provideAppRouter(navigation: Navigation.Base): Navigation<LaunchScreenMode> = navigation

    @Singleton
    @Provides
    fun provideCatalogRouter(navigation: Navigation.Base): CatalogRouter = navigation

    @Singleton
    @Provides
    fun provideDetailsRouter(navigation: Navigation.Base): DetailsRouter = navigation

    @Singleton
    @Provides
    fun provideBasketRouter(navigation: Navigation.Base): BasketRouter = navigation
}

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideProductsService(): ProductsService =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ProductsService::class.java)
}

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideProductsDatabase(context: Context): ProductsDB =
        Room.databaseBuilder(context.applicationContext, ProductsDB::class.java, "products.db").build()

    @Provides
    @Singleton
    fun provideProductsDao(db: ProductsDB): ProductsDao =
        db.productsDao()
}