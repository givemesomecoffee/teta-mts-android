package ru.givemesomecoffee.localdata.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.givemesomecoffee.localdata.db.dao.ActorsDao
import ru.givemesomecoffee.localdata.db.dao.CategoryDao
import ru.givemesomecoffee.localdata.db.dao.MovieDao
import ru.givemesomecoffee.localdata.db.dao.UserDao
import ru.givemesomecoffee.localdata.db.entity.*


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


