package info.bati11.whenit.ui.event

import android.app.Application
import androidx.lifecycle.*
import info.bati11.whenit.domain.Event

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    init {
        _event.value = Event(1, "hoge", "2019-12-17")
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
