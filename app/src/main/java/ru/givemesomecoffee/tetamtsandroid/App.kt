package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.givemesomecoffee.tetamtsandroid.data.local.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.di.ServiceLocator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("test", "blocking  thread")
        appContext = applicationContext
        CoroutineScope(Dispatchers.Default).launch {
            db = AppDatabase.getInstance()
        }
    }

    companion object {
        lateinit var appContext: Context
        lateinit var db: AppDatabase
        val repository get() = ServiceLocator.provideRepository()
    }

    //TODO: try to remove context
}


