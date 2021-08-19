package ru.givemesomecoffee.tetamtsandroid.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.ActorsDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.UserDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.*

@Database(
    entities = [MovieDto::class,
        CategoryDto::class,
        UserDto::class, UserFavourites::class,
        ActorDto::class, ActorsToMovies::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieDao(): MovieDao
    abstract fun CategoryDao(): CategoryDao
    abstract fun UserDao(): UserDao
    abstract fun ActorsDao(): ActorsDao

    companion object {
        private const val DATABASE_NAME = "Films.db"
        fun db(): AppDatabase {
            return getInstance()
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase().also { INSTANCE = it }
            }

        private fun buildDatabase(): AppDatabase {
            return Room.databaseBuilder(App.appContext, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}


