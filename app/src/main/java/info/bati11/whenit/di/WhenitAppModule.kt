package info.bati11.whenit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.bati11.whenit.database.repository.EventRepositoryImpl
import info.bati11.whenit.repository.EventRepository
import info.bati11.whenit.ui.ViewModelFactory
import info.bati11.whenit.ui.event.EventViewModel
import info.bati11.whenit.ui.event_create.EventCreateViewModel
import javax.inject.Singleton

@Module
interface WhenitAppModule {

    @Singleton
    @Binds
    fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

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

