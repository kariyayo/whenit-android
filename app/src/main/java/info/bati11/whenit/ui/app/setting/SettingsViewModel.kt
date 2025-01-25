package info.bati11.whenit.ui.app.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.bati11.whenit.database.SettingsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsStore: SettingsStore,
) : ViewModel() {

    data class UiState(
        val isEnableNotification: Boolean,
        val isEnableNotificationDay: Boolean,
        val isEnableNotificationWeek: Boolean,
        val isEnableNotificationMonth: Boolean,
    )

    private val dispatcher = Dispatchers.IO

    private val _uiState = MutableStateFlow(UiState(false, false, false, false))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.emit(
                UiState(
                    isEnableNotification = settingsStore.notification(),
                    isEnableNotificationDay = settingsStore.notificationDay(),
                    isEnableNotificationWeek = settingsStore.notificationWeek(),
                    isEnableNotificationMonth = settingsStore.notificationMonth(),
                )
            )
        }
    }

    fun saveNotification(enable: Boolean) {
        viewModelScope.launch(dispatcher) {
            settingsStore.saveNotification(enable)
            _uiState.emit(_uiState.value.copy(isEnableNotification = enable))
        }
    }

    fun saveNotificationDay(enable: Boolean) {
        viewModelScope.launch(dispatcher) {
            settingsStore.saveNotificationDay(enable)
            _uiState.emit(_uiState.value.copy(isEnableNotificationDay = enable))
        }
    }

    fun saveNotificationWeek(enable: Boolean) {
        viewModelScope.launch(dispatcher) {
            settingsStore.saveNotificationWeek(enable)
            _uiState.emit(_uiState.value.copy(isEnableNotificationWeek = enable))
        }
    }

    fun saveNotificationMonth(enable: Boolean) {
        viewModelScope.launch(dispatcher) {
            settingsStore.saveNotificationMonth(enable)
            _uiState.emit(_uiState.value.copy(isEnableNotificationMonth = enable))
        }
    }
}
