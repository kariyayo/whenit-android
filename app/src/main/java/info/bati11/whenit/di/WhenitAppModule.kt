package info.bati11.whenit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.bati11.whenit.ui.ViewModelFactory
import info.bati11.whenit.ui.event.EventViewModel
import info.bati11.whenit.ui.event_create.EventCreateViewModel

@Module
interface WhenitAppModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    fun bindEventViewModel(viewModel: EventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventCreateViewModel::class)
    fun bindEventCreateViewModel(viewModel: EventCreateViewModel): ViewModel
}

