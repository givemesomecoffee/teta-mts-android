package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.data.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.di.ServiceLocator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("test", "blocking  thread")
        appContext = applicationContext
        CoroutineScope(Dispatchers.Default).launch {

        Log.d("test", this.coroutineContext.toString())


                    db = AppDatabase.getInstance()}



        Log.d("test", "blocking  thread")
    }

    companion object {
        lateinit  var appContext: Context
        lateinit var db: AppDatabase
        val repository get() = ServiceLocator.provideRepository()
    }
}


