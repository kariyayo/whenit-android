package info.bati11.whenit

import android.app.Application
import androidx.preference.PreferenceManager
import com.facebook.flipper.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import info.bati11.whenit.di.DaggerWhenitAppComponent
import info.bati11.whenit.di.WhenitAppComponent
import info.bati11.whenit.notifications.reminder.RemindWorkerRegister
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {
    lateinit var appComponent: WhenitAppComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerWhenitAppComponent
            .factory()
            .create(this)
        appComponent.inject(this)

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

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
