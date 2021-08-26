package ru.givemesomecoffee.tetamtsandroid.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.presentation.MainActivity
import javax.inject.Inject

const val CHANNEL_ID = "123"

class RefreshDataWorker @Inject constructor(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    lateinit var domain: MoviesListCases
    private val notification = createNotification()

    override fun doWork(): Result {

        CoroutineScope(Dispatchers.IO).launch {
            domain.refreshMoviesList()
        }

        with(NotificationManagerCompat.from(context)) {
            notify(123, notification)
        }

        return Result.success()
    }

    private fun createNotification(): Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_search)
            .setContentTitle("Movie list refreshed")
            .setContentText("New top-20 list of movies ia available now!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .build()
    }

}





