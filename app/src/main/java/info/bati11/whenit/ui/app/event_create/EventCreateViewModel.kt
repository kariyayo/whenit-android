package info.bati11.whenit.ui.app.event_create

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import info.bati11.whenit.domain.Event
import info.bati11.whenit.domain.EventDate.toLocalDate
import info.bati11.whenit.repository.EventRepository
import info.bati11.whenit.ui.ValidationError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import javax.inject.Inject

@HiltViewModel
class EventCreateViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    data class UiState(
        val isCompleted: Boolean = false,
        val isSaveError: Boolean = false,
        val inputtedDateTimeInMilli: Long? = null,
        val formTitleError: ValidationError? = null,
        val formDateError: ValidationError? = null,
    ) {
        val inputtedDateString =
            inputtedDateTimeInMilli?.let {
                toLocalDate(inputtedDateTimeInMilli).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            } ?: ""
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onSaveClicked(titleValue: String, dateInMilli: Long?): Job {
        return viewModelScope.launch {
            val formTitleErr = if (titleValue.isBlank()) ValidationError.Required else null
            val formDateErr = if (dateInMilli == null) ValidationError.Required else null
            if (formTitleErr != null || formDateErr != null) {
                _uiState.value = _uiState.value.copy(
                    isCompleted = false,
                    formTitleError = formTitleErr,
                    formDateError = formDateErr,
                )
            } else {
                val localDate = toLocalDate(dateInMilli!!)
                runCatching {
                    eventRepository.add(
                        Event(
                            id = null,
                            title = titleValue,
                            year = localDate.year,
                            month = localDate.monthValue,
                            dayOfMonth = localDate.dayOfMonth
                        )
                    )
                    _uiState.value = _uiState.value.copy(
                        isCompleted = true,
                        isSaveError = false,
                        formTitleError = null,
                        formDateError = null,
                    )
                }.onFailure {
                    _uiState.value = UiState(
                        isCompleted = false,
                        isSaveError = true,
                    )
                }
            }
        }
    }

    fun inputDate(epochTime: Long) {
        _uiState.value = _uiState.value.copy(
            inputtedDateTimeInMilli = epochTime,
            formDateError = null,
        )
    }
}
