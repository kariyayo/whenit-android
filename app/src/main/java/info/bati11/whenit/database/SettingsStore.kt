package info.bati11.whenit.database

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import info.bati11.whenit.SettingsKeys
import javax.inject.Inject

class SettingsStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun notification(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION, false)
    }

    fun saveNotification(enable: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences
            .edit()
            .putBoolean(SettingsKeys.IS_SHOW_NOTIFICATION, enable)
            .apply()
    }

    fun notificationDay(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_DAY, false)
    }

    fun saveNotificationDay(enable: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences
            .edit()
            .putBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_DAY, enable)
            .apply()
    }

    fun notificationWeek(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_WEEK, false)
    }

    fun saveNotificationWeek(enable: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences
            .edit()
            .putBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_WEEK, enable)
            .apply()
    }

    fun notificationMonth(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_MONTH, false)
    }

    fun saveNotificationMonth(enable: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences
            .edit()
            .putBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_MONTH, enable)
            .apply()
    }
}
