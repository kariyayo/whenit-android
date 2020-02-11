package info.bati11.whenit.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import info.bati11.whenit.database.EventDatabase
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideEventDatabase(application: Application): EventDatabase {
        return Room.databaseBuilder(application, EventDatabase::class.java, "events")
            .build()
    }
}

