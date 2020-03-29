package info.bati11.whenit.ui.event.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventMenuViewModel @Inject constructor(
    val event: Event,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _navigateToEventEdit = MutableLiveData<Boolean>()
    val navigateToEventEdit: LiveData<Boolean>
        get() = _navigateToEventEdit

    private val _doneEventDelete = MutableLiveData<Boolean>()
    val doneEventDelete: LiveData<Boolean>
        get() = _doneEventDelete

    private val _showDeleteConfirmDialog = MutableLiveData<Boolean>()
    val showDeleteConfirmDialog: LiveData<Boolean>
        get() = _showDeleteConfirmDialog

    fun onEditClicked() {
        _navigateToEventEdit.value = true
    }

    fun onNavigatedToEventEdit() {
        _navigateToEventEdit.value = false
    }

    fun onDeleteClicked() {
        _showDeleteConfirmDialog.value = true
    }

    fun deleteEvent(): Job {
        return viewModelScope.launch {
            eventRepository.delete(event)
            _doneEventDelete.value = true
        }
    }

}
