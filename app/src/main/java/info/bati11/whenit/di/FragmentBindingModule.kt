package info.bati11.whenit.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import info.bati11.whenit.ui.app.event_create.EventCreateFragment
import info.bati11.whenit.ui.app.event_list.EventListFragment

@Module
interface FragmentBindingModule {

    @ContributesAndroidInjector
    fun contributeEventListFragmentInjector(): EventListFragment

    @ContributesAndroidInjector
    fun contributeEventCreateFragmentInjector(): EventCreateFragment
}
