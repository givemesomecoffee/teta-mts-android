package ru.givemesomecoffee.tetamtsandroid.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.givemesomecoffee.tetamtsandroid.data.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    @Query("SELECT * FROM categories WHERE id == :id")
    fun getCategoryById(id: Int): Category

    @Insert
    fun insertAll(list: List<Category>)

}