package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import androidx.work.*
import ru.givemesomecoffee.data.DaggerDataComponent
import ru.givemesomecoffee.data.DataComponent
import ru.givemesomecoffee.tetamtsandroid.di.AppComponent
import ru.givemesomecoffee.tetamtsandroid.di.DaggerAppComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {
    private val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDataService>(
        20, TimeUnit.MINUTES
    ) // flexInterval
        .build()

    @Inject
    lateinit var workerFactory: WorkerFactory

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }

    override fun onCreate() {
        super.onCreate()

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



