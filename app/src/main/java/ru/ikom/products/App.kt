package ru.ikom.products

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import ru.ikom.basket.di.BasketDepsImpl
import ru.ikom.catalog.di.CatalogDepsImpl
import ru.ikom.details.di.DetailsDepsImpl
import javax.inject.Inject

class App : Application(), ImageLoaderFactory {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this@App).build()
        appComponent.inject(this)
        CatalogDepsImpl.deps = appComponent
        DetailsDepsImpl.deps = appComponent
        BasketDepsImpl.deps = appComponent
    }

    override fun newImageLoader(): ImageLoader {
        return imageLoader
    }
}