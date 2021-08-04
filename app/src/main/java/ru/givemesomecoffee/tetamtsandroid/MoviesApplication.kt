package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.content.Context
import ru.givemesomecoffee.tetamtsandroid.data.db.AppDatabase

class MoviesApplication: Application() {

   override fun onCreate() {
      super.onCreate()
      appContext = applicationContext
   }

   companion object {

      lateinit  var appContext: Context

   }
}

