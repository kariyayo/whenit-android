package info.bati11.whenit

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.preference.PreferenceManager
import androidx.work.Configuration
import com.facebook.flipper.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import info.bati11.whenit.notifications.reminder.RemindWorkerRegister
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val isShowNotifications =
            PreferenceManager
                .getDefaultSharedPreferences(this)
                .getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION, false)
        if (isShowNotifications.not()) {
            RemindWorkerRegister(this).off()
        } else {
            RemindWorkerRegister(this).on()
        }

        FlipperInitializer.initFlipper(this)
    }
}
