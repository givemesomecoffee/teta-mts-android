package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    @Query("SELECT * FROM categories WHERE categoryId == :id")
    fun getCategoryById(id: Int): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Category>)

}
