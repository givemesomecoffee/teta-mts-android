package ru.givemesomecoffee.tetamtsandroid

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.presentation.MainActivity
import javax.inject.Inject

const val CHANNEL_ID = "123"

class RefreshDataService @Inject constructor(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    lateinit var domain: MoviesListCases
    override fun doWork(): Result {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_search)
            .setContentTitle("test")
            .setContentText("test text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)



        Log.d("workService", "im running")
        CoroutineScope(Dispatchers.IO).launch {
            domain.getMoviesList(null)
            Log.d("workService", "job finished")
        }

        with(NotificationManagerCompat.from(context)) {
            Log.d("workService", "job finished2")
            // notificationId is a unique int for each notification that you must define
            notify(123, builder.build())
        }
        Log.d("workService", "job finished2")
        return Result.success()
    }


}

class DaggerWorkerFactory @Inject constructor(private val domain: MoviesListCases) :
    WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        val workerKlass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        val constructor =
            workerKlass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is RefreshDataService -> {
                instance.domain = domain
            }
            // optionally, handle other workers
        }

        return instance
    }
}