package ru.ikom.details.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher
import ru.ikom.details.presentation.DetailsFragment
import ru.ikom.details.presentation.DetailsRouter
import ru.ikom.details.presentation.DetailsViewModel

@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    fun inject(fragment: DetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailsComponent
    }
}

@Module
class DetailsModule

interface DetailsComponentProvider {
    fun provideDetailsComponent(): DetailsComponent
}