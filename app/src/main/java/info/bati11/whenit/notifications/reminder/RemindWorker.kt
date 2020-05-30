package info.bati11.whenit.notifications.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import info.bati11.whenit.MainActivity
import info.bati11.whenit.R
import info.bati11.whenit.SettingsKeys
import info.bati11.whenit.di.DaggerWhenitAppComponent
import info.bati11.whenit.repository.EventRepository
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

class RemindWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    companion object {
        const val CHANNEL_ID = "WHENIT_REMINDER"
    }

    @Inject
    lateinit var eventRepository: EventRepository

    override fun doWork(): Result {
        val now = LocalDate.now()
        Timber.i("begin doWork(). now:${now}")
        try {
            val appComponent = DaggerWhenitAppComponent
                .factory()
                .create(applicationContext)
            appComponent.inject(this)

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val isShowNotifications = sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION, false)
            if (isShowNotifications) {
                val remindFactory = RemindMessageFactory(eventRepository)
                val content = remindFactory.createRemindContent(
                    now,
                    sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_DAY, false),
                    sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_WEEK, false),
                    sharedPreferences.getBoolean(SettingsKeys.IS_SHOW_NOTIFICATION_MONTH, false)
                )
                if (content.isNotBlank()) {
                    val channelName = "Reminder"
                    val title = "Whenit"
                    makeStatusNotification(applicationContext, channelName, now, title, content)
                }
            }
            return Result.success()
        } catch (e: Throwable) {
            Timber.e(e)
            return Result.failure()
        }
    }

    private fun makeStatusNotification(context: Context, channelName: String, now: LocalDate, title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // FIXME
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setGroupSummary(true)
            .setVibrate(LongArray(0))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationId = "${now.year}${now.monthValue}${now.dayOfMonth}"
        NotificationManagerCompat.from(context).notify(notificationId.toInt(), builder.build())
    }
}
