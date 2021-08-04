package ru.givemesomecoffee.tetamtsandroid.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.givemesomecoffee.tetamtsandroid.MoviesApplication
import ru.givemesomecoffee.tetamtsandroid.data.assets.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.assets.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.entity.Category

@Database(
    entities = [Movie::class, Category::class], version = 1
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieDao(): MovieDao
    abstract fun CategoryDao(): CategoryDao

    companion object {
        private const val DATABASE_NAME = "Films.db"

        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                MoviesApplication.appContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addCallback(callback)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance.MovieDao().insertAll(MoviesDataSourceImpl().getMovies())
                instance.CategoryDao().insertAll(MovieCategoriesDataSourceImpl().getCategories())
            }
        }

    }
}





