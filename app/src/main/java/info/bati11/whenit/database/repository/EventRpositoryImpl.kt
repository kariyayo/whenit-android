package info.bati11.whenit.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import info.bati11.whenit.database.EventDatabase
import info.bati11.whenit.database.entity.EventEntity
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val database: EventDatabase,
) : EventRepository {

    override val event: LiveData<Event?> = database.eventDao.selectLatest().map {
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
                    createdAt = Instant.now().toEpochMilli(),
                    updatedAt = Instant.now().toEpochMilli()
                )
            )
        }
    }

    override suspend fun update(event: Event) {
        withContext(Dispatchers.IO) {
            assert(event.id != null)
            val entity = database.eventDao.findById(event.id!!)
            assert(entity != null)
            database.eventDao.update(
                EventEntity(
                    id = event.id,
                    title = event.title,
                    year = event.year,
                    month = event.month,
                    dayOfMonth = event.dayOfMonth,
                    createdAt = entity!!.createdAt,
                    updatedAt = Instant.now().toEpochMilli()
                )
            )
        }
    }

    override suspend fun delete(event: Event) {
        withContext(Dispatchers.IO) {
            event.id?.let { database.eventDao.delete(it) }
        }
    }

    override fun allEvents(date: LocalDate): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                database.eventDao.allEventsOrderByNearly(date.monthValue, date.dayOfMonth)
            }
        ).flow.map { pagingData: PagingData<EventEntity> ->
            pagingData.map { entity ->
                Event(entity.id, entity.title, entity.year, entity.month, entity.dayOfMonth)
            }
        }
    }

    override fun findByDate(date: LocalDate): List<Event> {
        return database.eventDao.findByDate(date.monthValue, date.dayOfMonth).map {
            Event(it.id, it.title, it.year, it.month, it.dayOfMonth)
        }
    }
}
