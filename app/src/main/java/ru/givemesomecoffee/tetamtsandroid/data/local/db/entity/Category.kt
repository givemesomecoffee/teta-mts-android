package ru.givemesomecoffee.tetamtsandroid.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "categories")
data class Category(

    @ColumnInfo(name = "title")
    val title: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "categoryId")
    val id: Int?,
)

