package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import android.content.Context
import ru.givemesomecoffee.tetamtsandroid.di.AppComponent
import ru.givemesomecoffee.tetamtsandroid.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

}



