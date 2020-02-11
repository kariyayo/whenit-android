package info.bati11.whenit.repository

import androidx.lifecycle.LiveData
import info.bati11.whenit.domain.Event
import org.threeten.bp.LocalDate

interface EventRepository {

    val event: LiveData<Event?>

    suspend fun add(event: Event)

    suspend fun findEvents(date: LocalDate, limit: Int): List<Event>
}
