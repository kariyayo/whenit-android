package info.bati11.whenit.ui.event

import android.app.Application
import androidx.lifecycle.*
import info.bati11.whenit.database.getEventDatabase
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import timber.log.Timber

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val eventRepository = EventRepository(getEventDatabase(application))
    val event: LiveData<Event?> = eventRepository.event
    val eventLoaded: LiveData<Boolean> = Transformations.map(event) { true }
    val yearsFromEvent: LiveData<String> = Transformations.map(event) { ev ->
        ev?.years(System.currentTimeMillis())?.toString() ?: ""
    }
    val daysFromEvent: LiveData<String> = Transformations.map(event) { ev ->
        ev?.daysOfYears(System.currentTimeMillis())?.toString() ?: ""
    }
    val eventLabel: LiveData<String> = Transformations.map(event) { ev ->
        "${ev?.title} ( ${ev?.year}-${ev?.month}-${ev?.dayOfMonth} )"
    }

    private val _navigateToEventCreate = MutableLiveData<Boolean>()
    val navigateToEventCreate: LiveData<Boolean>
        get() = _navigateToEventCreate

    fun onCardMenuClicked() {
        Timber.i("onCardMenuClicked")
    }

    fun onFabClicked() {
        _navigateToEventCreate.value = true
    }

    fun onNavigatedToEventCreate() {
        _navigateToEventCreate.value = false
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
                return EventViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
