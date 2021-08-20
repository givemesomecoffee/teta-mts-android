package ru.givemesomecoffee.localdata.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryDto(

    @ColumnInfo(name = "title")
    val title: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "categoryId")
    val id: Int?,
)

