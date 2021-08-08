package ru.givemesomecoffee.tetamtsandroid.data.local.db.entity

import androidx.room.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int?,
    val name: String,
    val email: String,
    val password: String,
    val phone: Int?
)




