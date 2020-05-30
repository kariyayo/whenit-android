package info.bati11.whenit.notifications.reminder

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RemindWorkerRegister(private val context: Context) {

    companion object {
        private const val WORKER_NAME = "remind_worker"
    }

    fun on() {
        val workManager = WorkManager.getInstance(context)
        val now = LocalDateTime.now()
        val tomorrowAM10 = LocalDateTime.of(now.year, now.month, now.dayOfMonth, 10, 0).plusDays(1)
        val durationMinutes = org.threeten.bp.Duration.between(now, tomorrowAM10).toMinutes()
        Timber.i("$WORKER_NAME ON. durationMinutes:${durationMinutes}")
        val request = PeriodicWorkRequest.Builder(RemindWorker::class.java, 1, TimeUnit.DAYS)
            .setInitialDelay(durationMinutes, TimeUnit.MINUTES)
            .build()
        workManager.enqueueUniquePeriodicWork(WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, request)
    }

    fun off() {
        Timber.i("$WORKER_NAME OFF.")
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(WORKER_NAME)
    }
}
