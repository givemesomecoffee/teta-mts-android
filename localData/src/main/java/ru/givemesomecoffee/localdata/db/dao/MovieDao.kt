package ru.givemesomecoffee.localdata.db.dao

import androidx.room.*
import ru.givemesomecoffee.localdata.db.entity.MovieDto
import ru.givemesomecoffee.localdata.db.entity.MovieWithActors

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getAll(): List<MovieDto>

    @Query("SELECT * FROM movies WHERE categoryId == :categoryId  ORDER BY popularity DESC")
    fun getMoviesByCategory(categoryId: Int): List<MovieDto>

    @Transaction
    @Query("SELECT * FROM movies WHERE movieId == :id")
    fun getMovieById(id: Int): MovieWithActors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setAll(list: List<MovieDto>)

}
