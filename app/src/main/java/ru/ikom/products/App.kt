package ru.ikom.products

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import ru.ikom.catalog.di.CatalogComponent
import ru.ikom.catalog.di.CatalogComponentProvider
import ru.ikom.details.di.DetailsComponent
import ru.ikom.details.di.DetailsComponentProvider

class App : Application(), ImageLoaderFactory, CatalogComponentProvider, DetailsComponentProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

    override fun provideCatalogComponent(): CatalogComponent {
        return appComponent.catalogComponent().create()
    }

    override fun provideDetailsComponent(): DetailsComponent {
        return appComponent.detailsComponent().create()
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