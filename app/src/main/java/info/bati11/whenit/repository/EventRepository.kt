package info.bati11.whenit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.bati11.whenit.database.EventDatabase
import info.bati11.whenit.database.entity.EventEntity
import info.bati11.whenit.domain.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

class EventRepository(private val database: EventDatabase) {

    val event: LiveData<Event?> = Transformations.map(database.eventDao.selectLatest()) {
        if (it == null) {
            null
        } else {
            Event(it.id, it.title, it.year, it.month, it.dayOfMonth)
        }
    }

    suspend fun add(event: Event) {
        withContext(Dispatchers.IO) {
            database.eventDao.insert(
                EventEntity(
                    id = null,
                    title = event.title,
                    year = event.year,
                    month = event.month,
                    dayOfMonth = event.dayOfMonth,
                    createdAt = Instant.now().toEpochMilli()
                )
            )
        }
    }

    suspend fun findEvents(date: LocalDate, limit: Int): List<Event> {
        return withContext(Dispatchers.IO) {
            val entities =
                database.eventDao.selectOrderByNearly(date.monthValue, date.dayOfMonth, limit)
            entities.map { Event(it.id, it.title, it.year, it.month, it.dayOfMonth) }
        }
    }

}
