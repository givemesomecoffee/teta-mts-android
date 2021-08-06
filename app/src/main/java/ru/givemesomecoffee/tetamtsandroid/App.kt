package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.content.Context
import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.db.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        db = AppDatabase.getInstance()
    }

    companion object {
        lateinit  var appContext: Context
        lateinit var db: AppDatabase
    }
}


