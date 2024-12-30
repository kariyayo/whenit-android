package info.bati11.whenit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import info.bati11.whenit.database.dao.EventDao
import info.bati11.whenit.database.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract val eventDao: EventDao
}
