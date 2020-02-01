package info.bati11.whenit

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import info.bati11.whenit.di.DaggerWhenitAppComponent
import info.bati11.whenit.di.WhenitAppComponent
import timber.log.Timber

class Application : Application() {
    lateinit var appComponent: WhenitAppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        appComponent = DaggerWhenitAppComponent
            .builder()
            .application(this)
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
