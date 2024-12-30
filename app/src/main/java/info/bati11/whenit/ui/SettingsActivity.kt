package info.bati11.whenit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import info.bati11.whenit.R
import info.bati11.whenit.SettingsKeys
import info.bati11.whenit.notifications.reminder.RemindWorkerRegister

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings,
                SettingsFragment()
            )
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val isShowNotifications = PreferenceManager.getDefaultSharedPreferences(activity)
                .getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION, false)
            findPreference<CheckBoxPreference>(SettingsKeys.IS_SHOW_NOTIFICATION_DAY)?.isEnabled = isShowNotifications
            findPreference<CheckBoxPreference>(SettingsKeys.IS_SHOW_NOTIFICATION_WEEK)?.isEnabled = isShowNotifications
            findPreference<CheckBoxPreference>(SettingsKeys.IS_SHOW_NOTIFICATION_MONTH)?.isEnabled = isShowNotifications

            findPreference<SwitchPreferenceCompat>(SettingsKeys.IS_SHOW_NOTIFICATION)?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue is Boolean) {
                    if (newValue) {
                        RemindWorkerRegister(requireActivity().applicationContext).on()
                    } else {
                        RemindWorkerRegister(requireActivity().applicationContext).off()
                    }
                    findPreference<CheckBoxPreference>(SettingsKeys.IS_SHOW_NOTIFICATION_DAY)?.isEnabled = newValue
                    findPreference<CheckBoxPreference>(SettingsKeys.IS_SHOW_NOTIFICATION_WEEK)?.isEnabled = newValue
                    findPreference<CheckBoxPreference>(SettingsKeys.IS_SHOW_NOTIFICATION_MONTH)?.isEnabled = newValue
                    return@setOnPreferenceChangeListener true
                } else {
                    return@setOnPreferenceChangeListener false
                }
            }
        }
    }
}