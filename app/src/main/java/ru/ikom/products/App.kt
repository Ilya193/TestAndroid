package ru.ikom.products

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import ru.ikom.basket.di.BasketComponent
import ru.ikom.basket.di.BasketComponentProvider
import ru.ikom.catalog.di.CatalogComponent
import ru.ikom.catalog.di.CatalogComponentProvider
import ru.ikom.details.di.DetailsComponent
import ru.ikom.details.di.DetailsComponentProvider

class App : Application(),
    ImageLoaderFactory,
    CatalogComponentProvider,
    DetailsComponentProvider,
    BasketComponentProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this@App).build()
    }

    override fun provideCatalogComponent(): CatalogComponent {
        return appComponent.catalogComponent().create()
    }

    override fun provideDetailsComponent(): DetailsComponent {
        return appComponent.detailsComponent().create()
    }

    override fun provideBasketComponent(): BasketComponent {
        return appComponent.basketComponent().create()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.02)
                    .directory(cacheDir)
                    .build()
            }
            .build()
    }
}