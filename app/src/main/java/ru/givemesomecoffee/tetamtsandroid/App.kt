package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.content.Context
import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.db.AppDatabase

class App : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        db = AppDatabase.getDatabase()
        Log.d("room", "db init")
    }

}