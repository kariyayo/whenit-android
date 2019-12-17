package info.bati11.whenit.ui.event

import android.app.Application
import androidx.lifecycle.*
import info.bati11.whenit.database.getEventDatabase
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val eventRepository = EventRepository(getEventDatabase(application))
    val event: LiveData<Event?> = eventRepository.event

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                return EventViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
