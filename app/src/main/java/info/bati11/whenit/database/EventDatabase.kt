package info.bati11.whenit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import info.bati11.whenit.database.dao.EventDao
import info.bati11.whenit.database.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1)
abstract class EventDatabase : RoomDatabase() {
    abstract val eventDao: EventDao
}

private lateinit var INSTANCE: EventDatabase

fun getEventDatabase(context: Context): EventDatabase {
    synchronized(EventDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, EventDatabase::class.java, "events").build()
        }
    }
    return INSTANCE
}
