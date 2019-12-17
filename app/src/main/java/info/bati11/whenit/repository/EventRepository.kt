package info.bati11.whenit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import info.bati11.whenit.database.EventDatabase
import info.bati11.whenit.domain.Event

class EventRepository(database: EventDatabase) {
    val event: LiveData<Event?> = Transformations.map(database.eventDao.selectLatest()) {
        if (it == null) {
            null
        } else {
            Event(it.id, it.title, "${it.yyyy}-${it.mmdd}")
        }
    }
}
