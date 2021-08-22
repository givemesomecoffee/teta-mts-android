package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.*
import ru.givemesomecoffee.data.DaggerDataComponent
import ru.givemesomecoffee.data.DataComponent
import ru.givemesomecoffee.tetamtsandroid.di.AppComponent
import ru.givemesomecoffee.tetamtsandroid.di.DaggerAppComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDataService>(
        20, TimeUnit.MINUTES
    ) // flexInterval
        .setConstraints(constraints)
        .build()

    @Inject
    lateinit var workerFactory: WorkerFactory

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel name"
            val descriptionText = "channel desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("test", notificationManager.notificationChannels.toString())
        }
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        dataComponent = DaggerDataComponent.builder()
            .application(this)
            .build()

        appComponent = DaggerAppComponent.builder()
            .repository(dataComponent.repository())
            .userRepository(dataComponent.userRepository())
            .build()
appComponent.inject(this)
        configureWorkManager()

        WorkManager.getInstance(this)
            .enqueue(workRequest)
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
        private lateinit var dataComponent: DataComponent
    }

}



