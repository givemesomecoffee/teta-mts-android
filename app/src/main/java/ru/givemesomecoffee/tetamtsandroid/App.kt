package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import ru.givemesomecoffee.data.DaggerDataComponent
import ru.givemesomecoffee.tetamtsandroid.di.AppComponent
import ru.givemesomecoffee.tetamtsandroid.di.DaggerAppComponent
import ru.givemesomecoffee.tetamtsandroid.service.CHANNEL_ID
import ru.givemesomecoffee.tetamtsandroid.service.RefreshDataWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        initDaggerComponents()
        configureWorkManager()
        super.onCreate()
        createNotificationChannel()
        WorkManager.getInstance(this).enqueue(createRefreshDataWorkRequest())
    }

    private fun initDaggerComponents() {
        val dataComponent = DaggerDataComponent.builder()
            .application(this)
            .build()
        appComponent = DaggerAppComponent.builder()
            .repository(dataComponent.repository())
            .userRepository(dataComponent.userRepository())
            .build()
        appComponent.inject(this)
    }

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, config)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Movies list refreshed"
            val descriptionText = "Signals about movies list refreshed"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createRefreshDataWorkRequest(): PeriodicWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        return PeriodicWorkRequestBuilder<RefreshDataWorker>(
            24, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

}



