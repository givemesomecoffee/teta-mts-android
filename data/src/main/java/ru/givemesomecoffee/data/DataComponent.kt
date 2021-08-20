
package ru.givemesomecoffee.data

import android.app.Application
import dagger.*
import ru.givemesomecoffee.data.repository.Repository
import ru.givemesomecoffee.data.repository.UserRepository
import ru.givemesomecoffee.localdata.LocalDataModule
import ru.givemesomecoffee.remotedata.RemoteDataModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {

    fun repository(): Repository
    fun userRepository(): UserRepository

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): DataComponent
    }

}

@Module(includes = [LocalDataModule::class, RemoteDataModule::class])
interface DataModule{}



