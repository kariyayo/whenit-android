package info.bati11.whenit.di

import dagger.Module
import info.bati11.whenit.ui.event.EventComponent

@Module(subcomponents = [EventComponent::class])
object WhenitAppSubComponents
