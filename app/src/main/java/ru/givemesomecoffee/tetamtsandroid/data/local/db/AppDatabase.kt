package ru.givemesomecoffee.tetamtsandroid.data.local.db

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.data.local.db.assets.categories.CategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.db.assets.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category

@Database(
    entities = [Movie::class, Category::class], version = 1
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieDao(): MovieDao
    abstract fun CategoryDao(): CategoryDao

    companion object {
        private const val DATABASE_NAME = "Films.db"
        private var isSetup = false
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
            val temp = Room.databaseBuilder(
                App.appContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addCallback(callback)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            //TODO: find better solution
            //this is freaking weird calls to force callback
            temp.beginTransaction()
            temp.endTransaction()
            //

            if (isSetup) {
                Log.d("room", "i m in init")
                temp.MovieDao().setAll(MoviesDataSourceImpl().getMovies())
                temp.CategoryDao()
                    .insertAll(CategoriesDataSourceImpl().getCategories())
            }
            Log.d("room", "i gonna return this room")

            return temp
        }


        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                isSetup = true
                Log.d("room", "i should be called only once")
            }
        }
    }
}


