package ru.givemesomecoffee.tetamtsandroid.di


import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.local.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.data.remote.RemoteDatasourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.data.repository.UserRepository

object ServiceLocator {
    @Volatile
    var repository: Repository? = null

    @Volatile
    var userRepository: UserRepository? = null

    fun provideRepository(): Repository {
        synchronized(this) {
            return repository ?: createRepository()
        }
    }

    fun provideUserRepository(): UserRepository {
        synchronized(this) {
            return userRepository ?: createUserRepository()
        }
    }

    private fun createRepository(): Repository {
        val db = AppDatabase.getInstance()
        val remote = MoviesApiService.create()
        return Repository(LocalDatasourceImpl(db), RemoteDatasourceImpl(remote))
    }

    private fun createUserRepository(): UserRepository {
        val db = AppDatabase.getInstance()
        return UserRepository(LocalDatasourceImpl(db))
    }

}