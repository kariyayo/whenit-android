package info.bati11.whenit.ui.app.event_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

class EventListViewModel @Inject constructor(
    application: Application,
    private val eventRepository: EventRepository
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
        get() = _events
    val eventLoaded: LiveData<Boolean> = Transformations.map(events) { true }

    private val _navigateToEventCreate = MutableLiveData<Boolean>()
    val navigateToEventCreate: LiveData<Boolean>
        get() = _navigateToEventCreate

    private val _showSelectedEventMenu = MutableLiveData<Event?>()
    val showSelectedEventMenu: LiveData<Event?>
        get() = _showSelectedEventMenu

    fun loadEvents(date: LocalDate) {
        uiScope.launch {
            _events.value = eventRepository.findEvents(date, 10)
        }
    }

    fun displayEventMenu(event: Event) {
        Timber.i("onCardMenuClicked. event: ${event}")
        _showSelectedEventMenu.value = event
    }

    fun displayEventMenuComplete() {
        _showSelectedEventMenu.value = null
    }

    fun onFabClicked() {
        _navigateToEventCreate.value = true
    }

    fun onNavigatedToEventCreate() {
        _navigateToEventCreate.value = false
    }

}
