package info.bati11.whenit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import kotlinx.android.synthetic.main.activity_licenses.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val isShowNotifications = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("notifications", false)
            findPreference<CheckBoxPreference>("notifications_day")?.isEnabled = isShowNotifications
            findPreference<CheckBoxPreference>("notifications_week")?.isEnabled = isShowNotifications
            findPreference<CheckBoxPreference>("notifications_month")?.isEnabled = isShowNotifications

            findPreference<SwitchPreferenceCompat>("notifications")?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue is Boolean) {
                    findPreference<CheckBoxPreference>("notifications_day")?.isEnabled = newValue
                    findPreference<CheckBoxPreference>("notifications_week")?.isEnabled = newValue
                    findPreference<CheckBoxPreference>("notifications_month")?.isEnabled = newValue
                    return@setOnPreferenceChangeListener true
                } else {
                    return@setOnPreferenceChangeListener false
                }
            }
        }
    }
}