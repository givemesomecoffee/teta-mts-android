package ru.givemesomecoffee.tetamtsandroid.di


import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.local.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository

object ServiceLocator {
    @Volatile
    var repository: Repository? = null

    fun provideRepository(): Repository {
        synchronized(this) {
            return repository ?: createRepository()
        }
    }

    private fun createRepository(): Repository {
        val db = AppDatabase.getInstance()
        return Repository(LocalDatasourceImpl(db))
    }

}