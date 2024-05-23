package ru.ikom.products

import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.ikom.catalog.di.CatalogComponent
import ru.ikom.catalog.presentation.CatalogRouter
import ru.ikom.network.products.ProductsService
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AppSubcomponents::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun catalogComponent(): CatalogComponent.Factory
}

@Module(subcomponents = [CatalogComponent::class])
class AppSubcomponents

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNavigationBase(): Navigation.Base = Navigation.Base()

    @Singleton
    @Provides
    fun provideAppRouter(navigation: Navigation.Base): Navigation<LaunchScreenMode> = navigation

    @Singleton
    @Provides
    fun provideCatalogRouter(navigation: Navigation.Base): CatalogRouter = navigation

    @Provides
    @Singleton
    fun provideProductsService(): ProductsService =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ProductsService::class.java)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideMainViewModelFactory(
        navigation: Navigation<LaunchScreenMode>,
    ): MainViewModel.Factory =
        MainViewModel.Factory(navigation)
}