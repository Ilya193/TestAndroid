package ru.ikom.details.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.ikom.common.Feature
import ru.ikom.details.presentation.DetailsFragment
import ru.ikom.details.presentation.DetailsRouter
import ru.ikom.details.presentation.DetailsViewModel
import kotlin.properties.Delegates

@Component(modules = [DetailsModule::class], dependencies = [DetailsDeps::class])
@Feature
interface DetailsComponent {
    fun inject(fragment: DetailsFragment)

    @Component.Builder
    interface Builder {
        fun deps(deps: DetailsDeps): Builder

        fun build(): DetailsComponent
    }
}

@Module
class DetailsModule {
    @Provides
    @Feature
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

interface DetailsDeps {
    val detailsRouter: DetailsRouter
}

interface DetailsDepsProvider {
    var deps: DetailsDeps

    companion object : DetailsDepsProvider by DetailsDepsImpl
}

object DetailsDepsImpl : DetailsDepsProvider {
    override var deps: DetailsDeps by Delegates.notNull()
}

class DetailsComponentViewModel : ViewModel() {
    val detailsComponent = DaggerDetailsComponent.builder().deps(DetailsDepsProvider.deps).build()
}