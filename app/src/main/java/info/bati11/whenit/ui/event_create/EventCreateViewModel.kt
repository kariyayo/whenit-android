package info.bati11.whenit.ui.event_create

import android.app.Application
import androidx.lifecycle.*
import info.bati11.whenit.R
import info.bati11.whenit.database.getEventDatabase
import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class EventCreateViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val eventRepository = EventRepository(getEventDatabase(application))

    private val _navigateToEvent = MutableLiveData<Boolean>()
    val navigateToEvent: LiveData<Boolean>
        get() = _navigateToEvent

    private val _showDatePickerEvent = MutableLiveData<Boolean>()
    val showDatePickerDialogEvent: LiveData<Boolean>
        get() = _showDatePickerEvent

    private val errorMessageRequired = application.getString(R.string.input_helper_text_required)

    private val _formTitle = MutableLiveData<String>()
    private val _formTitleErrMsg = MutableLiveData<String>()
    val formTitleErrMsg: LiveData<String>
        get() = _formTitleErrMsg

    private val _formDateInMilli = MutableLiveData<Long>()
    val formDate: LiveData<String>
        get() = Transformations.map(_formDateInMilli) { epochTimeInMilli: Long ->
            val epochDay = toEpochDay(epochTimeInMilli)
            LocalDate.ofEpochDay(epochDay)
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        }
    private val _formDateErrMsg = MutableLiveData<String>()
    val formDateErrMsg: LiveData<String>
        get() = _formDateErrMsg

    fun onSaveClicked() {
        viewModelScope.launch {
            val titleValue = _formTitle.value.orEmpty().trim()
            _formTitleErrMsg.value = if (titleValue.isBlank()) errorMessageRequired else null
            val dateInMilliValue = _formDateInMilli.value ?: 0L
            _formDateErrMsg.value = if (dateInMilliValue == 0L) errorMessageRequired else null
            if (_formTitleErrMsg.value == null && _formDateErrMsg.value == null) {
                val localDate = LocalDate.ofEpochDay(toEpochDay(dateInMilliValue))
                eventRepository.add(
                    Event(
                        id = null,
                        title = titleValue,
                        year = localDate.year,
                        month = localDate.monthValue,
                        dayOfMonth = localDate.dayOfMonth
                    )
                )
                _navigateToEvent.value = true
            }
        }
    }

    fun onCloseClicked() {
        _navigateToEvent.value = true
    }

    fun onNavigatedToEvent() {
        _navigateToEvent.value = false
    }

    fun onDateEditTextClicked() {
        _showDatePickerEvent.value = true
    }

    fun onDismissDatePicker() {
        _showDatePickerEvent.value = false
    }

    fun inputTitle(title: String) {
        _formTitle.value = title
        if (title.isBlank()) {
            _formTitleErrMsg.value = errorMessageRequired
        } else if (_formTitleErrMsg.value != null) {
            _formTitleErrMsg.value = null
        }
    }

    fun inputDate(epochTime: Long) {
        _formDateInMilli.value = epochTime
        if (_formDateErrMsg.value != null) {
            _formDateErrMsg.value = null
        }
    }

    private fun toEpochDay(epochTime: Long): Long = epochTime / 1000 / 60 / 60 / 24

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventCreateViewModel::class.java)) {
                return EventCreateViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
