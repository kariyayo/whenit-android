package info.bati11.whenit.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.bati11.whenit.database.EventDatabase
import info.bati11.whenit.database.entity.EventEntity
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val database: EventDatabase): EventRepository {

    override val event: LiveData<Event?> = Transformations.map(database.eventDao.selectLatest()) {
        Timber.i("selectLatest. database is: ${database}")
        if (it == null) {
            null
        } else {
            Event(it.id, it.title, it.year, it.month, it.dayOfMonth)
        }
    }

    override suspend fun add(event: Event) {
        Timber.i("add. database is: ${database}")
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

    override suspend fun findEvents(date: LocalDate, limit: Int): List<Event> {
        Timber.i("findEvents. database is: ${database}")
        return withContext(Dispatchers.IO) {
            val entities =
                database.eventDao.selectOrderByNearly(date.monthValue, date.dayOfMonth, limit)
            entities.map { Event(it.id, it.title, it.year, it.month, it.dayOfMonth) }
        }
    }

}
