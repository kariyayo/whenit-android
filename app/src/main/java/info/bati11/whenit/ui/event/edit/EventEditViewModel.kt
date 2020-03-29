package info.bati11.whenit.ui.event.edit

import androidx.lifecycle.*
import info.bati11.whenit.domain.Event
import info.bati11.whenit.domain.EventDate
import info.bati11.whenit.repository.EventRepository
import info.bati11.whenit.ui.ValidationError
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import javax.inject.Inject

class EventEditViewModel @Inject constructor(
    private val event: Event,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _popBack = MutableLiveData(false)
    val popBack: LiveData<Boolean>
        get() = _popBack

    private val _showDatePickerEvent = MutableLiveData(false)
    val showDatePickerDialogEvent: LiveData<Boolean>
        get() = _showDatePickerEvent

    private val _formTitle = MutableLiveData(event.title)
    val formTitle: LiveData<String>
        get() = _formTitle
    private val _formTitleErr = MutableLiveData<ValidationError>()
    val formTitleErr: LiveData<ValidationError>
        get() = _formTitleErr

    private val _formDateInMilli = MutableLiveData(event.epochMilliSeconds())
    val formDate: LiveData<String>
        get() = Transformations.map(_formDateInMilli) {
            EventDate.toLocalDate(it).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        }
    private val _formDateErr = MutableLiveData<ValidationError>()
    val formDateErr: LiveData<ValidationError>
        get() = _formDateErr

    fun onSaveClicked(): Job {
        return viewModelScope.launch {
            val titleValue = _formTitle.value.orEmpty().trim()
            _formTitleErr.value = if (titleValue.isBlank()) ValidationError.Required else null
            val dateInMilliValue = _formDateInMilli.value ?: 0L
            _formDateErr.value = if (dateInMilliValue == 0L) ValidationError.Required else null
            if (_formTitleErr.value == null && _formDateErr.value == null) {
                val localDate = EventDate.toLocalDate(dateInMilliValue)
                eventRepository.update(
                    Event(
                        id = event.id,
                        title = titleValue,
                        year = localDate.year,
                        month = localDate.monthValue,
                        dayOfMonth = localDate.dayOfMonth
                    )
                )
                _popBack.value = true
            }
        }
    }

    fun onCloseClicked() {
        _popBack.value = true
    }

    fun onPopBacked() {
        _popBack.value = false
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
