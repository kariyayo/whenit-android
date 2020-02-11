package info.bati11.whenit.ui.event.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.bati11.whenit.domain.Event
import javax.inject.Inject

class EventMenuViewModel @Inject constructor(private val event: Event) : ViewModel() {

    private val _navigateToEventEdit = MutableLiveData<Boolean>()
    val navigateToEventEdit: LiveData<Boolean>
        get() = _navigateToEventEdit

    private val _navigateToEventRemove = MutableLiveData<Boolean>()
    val navigateToEventRemove: LiveData<Boolean>
        get() = _navigateToEventRemove

    fun onEditClicked() {
        _navigateToEventEdit.value = true
    }

    fun onNavigatedToEventEdit() {
        _navigateToEventEdit.value = false
    }

    fun onRemoveClicked() {
        _navigateToEventRemove.value = true
    }

    fun onNavigatedToEventRemove() {
        _navigateToEventRemove.value = false
    }

}
