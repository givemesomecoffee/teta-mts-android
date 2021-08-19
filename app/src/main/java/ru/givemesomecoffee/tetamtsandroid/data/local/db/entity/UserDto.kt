package ru.givemesomecoffee.tetamtsandroid.data.local.db.entity

import androidx.room.*

@Entity(tableName = "users")
data class UserDto(
    @PrimaryKey(autoGenerate = false)
    var userId: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val phone: Int? = null,
    val token: String? = null
)




