package info.bati11.whenit

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import info.bati11.whenit.di.DaggerWhenitAppComponent
import info.bati11.whenit.di.WhenitAppComponent
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasAndroidInjector {
    lateinit var appComponent: WhenitAppComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerWhenitAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FlipperInitializer.initFlipper(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
