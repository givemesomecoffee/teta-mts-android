package ru.givemesomecoffee.tetamtsandroid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE categoryId == :id")
    fun getAllByCategory(id: Int): List<Movie>

    @Insert
    fun setAll(list: List<Movie>)
}