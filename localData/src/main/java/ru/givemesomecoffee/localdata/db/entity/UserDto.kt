package ru.givemesomecoffee.localdata.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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




