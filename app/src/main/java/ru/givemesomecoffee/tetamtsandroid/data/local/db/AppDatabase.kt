package ru.givemesomecoffee.tetamtsandroid.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MovieDao(): MovieDao
    abstract fun CategoryDao(): CategoryDao
    abstract fun UserDao(): UserDao
    abstract fun ActorsDao(): ActorsDao

}


