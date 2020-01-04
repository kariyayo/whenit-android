package info.bati11.whenit.ui.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import info.bati11.whenit.database.getEventDatabase
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

class EventViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val eventRepository = EventRepository(getEventDatabase(application))
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
        get() = _events
    val eventLoaded: LiveData<Boolean> = Transformations.map(events) { true }

    private val _navigateToEventCreate = MutableLiveData<Boolean>()
    val navigateToEventCreate: LiveData<Boolean>
        get() = _navigateToEventCreate

    private val _showEventMenu = MutableLiveData<Long?>()
    val showEventMenu: LiveData<Long?>
        get() = _showEventMenu

    fun loadEvents(date: LocalDate) {
        uiScope.launch {
            _events.value = eventRepository.findEvents(date, 10)
        }
    }

    fun onCardMenuClicked(eventId: Long) {
        Timber.i("onCardMenuClicked. eventId: ${eventId}")
        _showEventMenu.value = eventId
    }

    fun onFabClicked() {
        _navigateToEventCreate.value = true
    }

    fun onNavigatedToEventCreate() {
        _navigateToEventCreate.value = false
    }

}
