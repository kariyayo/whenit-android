package info.bati11.whenit.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import info.bati11.whenit.ui.event_menu.EventMenuComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [WhenitAppModule::class, WhenitAppSubComponents::class])
interface WhenitAppComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {
        fun build(): WhenitAppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun eventMenuComponent(): EventMenuComponent.Factory
}
