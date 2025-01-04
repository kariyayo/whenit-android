package info.bati11.whenit.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.bati11.whenit.database.repository.EventRepositoryImpl
import info.bati11.whenit.repository.EventRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WhenitAppModule {

    @Singleton
    @Binds
    fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository
}
