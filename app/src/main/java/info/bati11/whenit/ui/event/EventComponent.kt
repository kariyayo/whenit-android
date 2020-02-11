package info.bati11.whenit.ui.event

import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Subcomponent
import info.bati11.whenit.domain.Event

@Subcomponent(modules = [EventModule::class])
interface EventComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance event: Event): EventComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory
}
