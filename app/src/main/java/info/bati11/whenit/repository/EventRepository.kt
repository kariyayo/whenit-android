package info.bati11.whenit.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import info.bati11.whenit.domain.Event
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface EventRepository {

    val event: LiveData<Event?>

    suspend fun add(event: Event)

    suspend fun update(event: Event)

    suspend fun delete(event: Event)

    fun allEvents(date: LocalDate): Flow<PagingData<Event>>

    fun findByDate(date: LocalDate): List<Event>
}
