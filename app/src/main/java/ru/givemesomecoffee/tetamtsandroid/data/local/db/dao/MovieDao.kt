package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.*
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieDto>

    @Query("SELECT * FROM movies WHERE categoryId == :categoryId")
    fun getMoviesByCategory(categoryId: Int): List<MovieDto>

    @Transaction
    @Query("SELECT * FROM movies WHERE movieId == :id")
    fun getMovieById(id: Int): MovieWithActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAll(list: List<MovieDto>)

}
