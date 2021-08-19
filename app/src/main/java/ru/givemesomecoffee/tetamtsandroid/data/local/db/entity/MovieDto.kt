package ru.givemesomecoffee.tetamtsandroid.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDto(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movieId")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "rateScore")
    val rateScore: Float,

    @ColumnInfo(name = "ageRestriction")
    val ageRestriction: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "categoryId")
    val categoryId: Int

)