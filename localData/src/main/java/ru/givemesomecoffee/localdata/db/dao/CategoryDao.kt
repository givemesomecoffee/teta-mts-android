package ru.givemesomecoffee.localdata.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.givemesomecoffee.localdata.db.entity.CategoryDto

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAll(): List<CategoryDto>

    @Query("SELECT * FROM categories WHERE categoryId == :id")
    fun getCategoryById(id: Int): CategoryDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CategoryDto>)

}
