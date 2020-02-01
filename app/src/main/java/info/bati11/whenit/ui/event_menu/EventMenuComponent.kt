package info.bati11.whenit.ui.event_menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import info.bati11.whenit.di.ViewModelKey
import info.bati11.whenit.domain.Event

@Module
interface EventMenuModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventMenuViewModel::class)
    fun bindEventMenuViewModel(viewModel: EventMenuViewModel): ViewModel
}

@Subcomponent(modules = [EventMenuModule::class])
interface EventMenuComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance event: Event): EventMenuComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory
}
