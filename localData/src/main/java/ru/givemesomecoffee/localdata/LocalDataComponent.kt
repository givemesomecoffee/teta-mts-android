package ru.givemesomecoffee.localdata

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.givemesomecoffee.localdata.db.AppDatabase
import ru.givemesomecoffee.localdata.db.LocalDatasourceImpl
import javax.inject.Singleton

@Module(includes = [LocalDataBindings::class])
class LocalDataModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "DATABASE_NAME"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}

@Module
internal interface LocalDataBindings {


    @Binds
    @Suppress("UNUSED")
    fun bind_LocalDatasourceImpl_to_LocalDatasource(localDatasourceImpl: LocalDatasourceImpl): LocalDatasource
}