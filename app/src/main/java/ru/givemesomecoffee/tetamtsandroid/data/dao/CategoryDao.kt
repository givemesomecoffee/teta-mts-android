package ru.givemesomecoffee.tetamtsandroid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.givemesomecoffee.tetamtsandroid.data.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    @Insert
    fun setAll(list: List<Category>)

}