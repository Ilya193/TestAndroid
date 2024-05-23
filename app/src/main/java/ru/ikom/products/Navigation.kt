package ru.ikom.products

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ikom.catalog.presentation.CatalogFragment
import ru.ikom.catalog.presentation.CatalogRouter
import javax.inject.Inject

interface Navigation<T>  {
    fun read(): StateFlow<T>
    fun update(value: T)

    class Base : Navigation<LaunchScreenMode>, CatalogRouter {
        private val screen = MutableStateFlow<LaunchScreenMode>(LaunchScreenMode.Empty())

        override fun read(): StateFlow<LaunchScreenMode> = screen.asStateFlow()

        override fun update(value: LaunchScreenMode) {
            screen.value = value
        }

        override fun openDetails(data: String) {
            TODO()
        }

        override fun coup() {
            update(LaunchScreenMode.Coup())
        }
    }
}

interface LaunchScreenMode {
    fun show(supportFragmentManager: FragmentManager, container: Int) = Unit

    abstract class Replace(
        private val fragment: Fragment
    ) : LaunchScreenMode {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.commit {
                replace(container, fragment)
            }
        }
    }

    class Coup : LaunchScreenMode

    class Empty : LaunchScreenMode
}

class CatalogScreen : LaunchScreenMode.Replace(CatalogFragment.newInstance())