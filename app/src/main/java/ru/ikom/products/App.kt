package ru.ikom.products

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import ru.ikom.catalog.di.CatalogComponent
import ru.ikom.catalog.di.CatalogComponentProvider

class App : Application(), ImageLoaderFactory, CatalogComponentProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

    override fun provideCatalogComponent(): CatalogComponent {
        return appComponent.catalogComponent().create()
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