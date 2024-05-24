package ru.ikom.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainViewModel(
    private val navigation: Navigation<LaunchScreenMode>
) : ViewModel() {

    fun init(first: Boolean) {
        if (first) navigation.update(CatalogScreen())
    }

    fun readScreen(): StateFlow<LaunchScreenMode> = navigation.read()

    class Factory(
        private val navigation: Navigation<LaunchScreenMode>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(navigation) as T
        }
    }
}