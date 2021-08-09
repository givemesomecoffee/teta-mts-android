package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.User
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserFavourites
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites


@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users WHERE userId == :id")
    fun getUserData(id: Int): UserWithFavourites

    @Query("SELECT * FROM users WHERE email == :email AND password == :password")
    fun checkUser(email: String?, password: Int?): User

    @Insert
    fun setUser(user: User)

    @Insert
    fun setUserFavourites(favourites: List<UserFavourites>)
}