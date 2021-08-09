package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE categoryId == :categoryId")
    fun getMoviesByCategory(categoryId: Int): List<Movie>

    @Transaction
    @Query("SELECT * FROM movies WHERE movieId == :id")
    fun getMovieById(id: Int): MovieWithActors

    @Insert
    fun setAll(list: List<Movie>)

}
