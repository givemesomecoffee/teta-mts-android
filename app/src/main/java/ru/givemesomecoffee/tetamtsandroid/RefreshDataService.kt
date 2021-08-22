package ru.givemesomecoffee.tetamtsandroid

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import javax.inject.Inject

class RefreshDataService @Inject constructor(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams){
    lateinit var domain: MoviesListCases
    override fun doWork(): Result {
        Log.d("workService", "im running")
        CoroutineScope(Dispatchers.IO).launch {
            domain.getMoviesList(null)
            Log.d("workService", "job finished")
        }

        return Result.success()
    }

}

class DaggerWorkerFactory @Inject constructor(private val domain: MoviesListCases) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {

        val workerKlass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        val constructor = workerKlass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
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