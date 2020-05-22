package info.bati11.whenit.di

import android.content.Context
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
    fun provideEventDatabase(context: Context): EventDatabase {
        return Room.databaseBuilder(context, EventDatabase::class.java, "events")
            .build()
    }
}

