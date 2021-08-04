package ru.givemesomecoffee.tetamtsandroid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE categoryId == :categoryId")
    fun getMoviesByCategory(categoryId: Int): List<Movie>

    @Query("SELECT * FROM movies WHERE id == :id")
    fun getMovieById(id: Long): Movie



    @Insert
    fun insertAll(films: List<Movie>)

    @Insert
    fun insert(film: Movie): Long

    @Update
    fun update(film: Movie)

}