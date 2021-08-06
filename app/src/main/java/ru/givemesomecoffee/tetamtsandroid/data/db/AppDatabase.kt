package ru.givemesomecoffee.tetamtsandroid.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.data.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

@Database(entities = [Movie::class, Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
    abstract fun CategoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(App.appContext, AppDatabase::class.java, "movie.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }

}