package ru.givemesomecoffee.tetamtsandroid.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import javax.inject.Inject

class DaggerRefreshDataWorkerFactory @Inject constructor(private val domain: MoviesListCases) :
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
            is RefreshDataWorker -> {
                instance.domain = domain
            }
        }

        return instance
    }
}