package info.bati11.whenit.ui.event

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import info.bati11.whenit.di.ViewModelKey
import info.bati11.whenit.ui.event.edit.EventEditViewModel
import info.bati11.whenit.ui.event.menu.EventMenuViewModel

@Module
interface EventModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventMenuViewModel::class)
    fun bindEventMenuViewModel(viewModel: EventMenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventEditViewModel::class)
    fun bindEventEditViewModel(viewModel: EventEditViewModel): ViewModel
}
