package info.bati11.whenit

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.soloader.SoLoader
import com.jakewharton.threetenabp.AndroidThreeTen
import info.bati11.whenit.di.DaggerWhenitAppComponent
import info.bati11.whenit.di.WhenitAppComponent
import timber.log.Timber


class App : Application() {
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

        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()
        }
    }
}
