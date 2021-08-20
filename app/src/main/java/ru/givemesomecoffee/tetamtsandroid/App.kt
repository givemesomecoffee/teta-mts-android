package ru.givemesomecoffee.tetamtsandroid

import android.app.Application
import ru.givemesomecoffee.data.DaggerDataComponent
import ru.givemesomecoffee.data.DataComponent
import ru.givemesomecoffee.tetamtsandroid.di.AppComponent
import ru.givemesomecoffee.tetamtsandroid.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        dataComponent = DaggerDataComponent.builder()
            .application(this)
            .build()

        appComponent = DaggerAppComponent.builder()
            .repository(dataComponent.repository())
            .userRepository(dataComponent.userRepository())
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
        private lateinit var dataComponent: DataComponent
    }

}



