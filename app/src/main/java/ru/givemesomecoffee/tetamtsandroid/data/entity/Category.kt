package ru.givemesomecoffee.tetamtsandroid.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    val title: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int
)