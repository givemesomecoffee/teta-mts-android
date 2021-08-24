package ru.givemesomecoffee.localdata.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["userId", "categoryId"])
data class UserFavourites(
    val userId: Int,
    val categoryId: Int
)

data class UserWithFavourites(
    @Embedded val user: UserDto,

    @Relation(
        parentColumn = "userId",
        entityColumn = "categoryId",
        associateBy = Junction(UserFavourites::class)
    )
    val categories: List<CategoryDto>
)

