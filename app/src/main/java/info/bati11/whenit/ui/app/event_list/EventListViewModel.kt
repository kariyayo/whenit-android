package info.bati11.whenit.ui.app.event_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    val eventRepository: EventRepository
) : ViewModel() {

    val events: Flow<PagingData<Event>> =
        eventRepository
            .allEvents(LocalDate.now())
            .cachedIn(viewModelScope)

    fun deleteEvent(event: Event): Job {
        return viewModelScope.launch {
            eventRepository.delete(event)
        }
    }
}
