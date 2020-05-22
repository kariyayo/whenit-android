package info.bati11.whenit.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import info.bati11.whenit.App
import info.bati11.whenit.ui.event.EventComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    DatabaseModule::class,
    WhenitAppModule::class,
    WhenitAppSubComponents::class,
    FragmentBindingModule::class
])
interface WhenitAppComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): WhenitAppComponent
    }

    fun inject(app: App)

    fun eventMenuComponent(): EventComponent.Factory
}
