package info.bati11.whenit.ui.app.event_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    eventRepository: EventRepository
) : ViewModel() {

    private val eventListBoundaryCallback = object : PagedList.BoundaryCallback<Event>() {
        override fun onZeroItemsLoaded() {
            _eventNothing.value = true
        }
    }

    val events = LivePagedListBuilder(
        eventRepository.allEvents(LocalDate.now()),
        PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(40)
            .setPrefetchDistance(20)
            .build()
    )
        .setBoundaryCallback(eventListBoundaryCallback)
        .build()

    private val _eventNothing = MutableLiveData(false)
    val eventNothing: LiveData<Boolean>
        get() = _eventNothing

    private val _navigateToEventCreate = MutableLiveData<Boolean>()
    val navigateToEventCreate: LiveData<Boolean>
        get() = _navigateToEventCreate

    private val _showSelectedEventMenu = MutableLiveData<Event?>()
    val showSelectedEventMenu: LiveData<Event?>
        get() = _showSelectedEventMenu

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
