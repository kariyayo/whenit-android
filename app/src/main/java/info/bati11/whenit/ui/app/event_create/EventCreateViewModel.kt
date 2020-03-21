package info.bati11.whenit.ui.app.event_create

import androidx.lifecycle.*
import info.bati11.whenit.domain.Event
import info.bati11.whenit.domain.EventDate.toLocalDate
import info.bati11.whenit.repository.EventRepository
import info.bati11.whenit.ui.ValidationError
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import javax.inject.Inject

class EventCreateViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _navigateToEvent = MutableLiveData<Boolean>()
    val navigateToEvent: LiveData<Boolean>
        get() = _navigateToEvent

    private val _showDatePickerEvent = MutableLiveData<Boolean>()
    val showDatePickerDialogEvent: LiveData<Boolean>
        get() = _showDatePickerEvent

    private val _formTitle = MutableLiveData<String>()
    private val _formTitleErr = MutableLiveData<ValidationError>()
    val formTitleErr: LiveData<ValidationError>
        get() = _formTitleErr

    private val _formDateInMilli = MutableLiveData<Long>()
    val formDate: LiveData<String>
        get() = Transformations.map(_formDateInMilli) {
            toLocalDate(it).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        }
    private val _formDateErr = MutableLiveData<ValidationError>()
    val formDateErr: LiveData<ValidationError>
        get() = _formDateErr

    fun onSaveClicked() {
        viewModelScope.launch {
            val titleValue = _formTitle.value.orEmpty().trim()
            _formTitleErr.value = if (titleValue.isBlank()) ValidationError.Required else null
            val dateInMilliValue = _formDateInMilli.value ?: 0L
            _formDateErr.value = if (dateInMilliValue == 0L) ValidationError.Required else null
            if (_formTitle.value == null && _formDateErr.value == null) {
                val localDate = toLocalDate(dateInMilliValue)
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
            _formTitleErr.value = ValidationError.Required
        } else if (_formTitleErr.value != null) {
            _formTitleErr.value = null
        }
    }

    fun inputDate(epochTime: Long) {
        _formDateInMilli.value = epochTime
        if (_formDateErr.value != null) {
            _formDateErr.value = null
        }
    }

}
