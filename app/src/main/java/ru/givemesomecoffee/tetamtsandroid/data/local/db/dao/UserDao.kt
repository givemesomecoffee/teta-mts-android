package ru.givemesomecoffee.tetamtsandroid.data.local.db.dao

import androidx.room.*
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.User
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserFavourites
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites


@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users WHERE userId == :id")
    fun getUserData(id: Int): UserWithFavourites

    @Query("SELECT * FROM users WHERE email == :email AND password == :password")
    fun checkUser(email: String?, password: String?): User

    @Insert
    fun setUser(user: User)

    @Insert
    fun setUserFavourites(favourites: List<UserFavourites>)

    @Query("UPDATE users SET token = :token WHERE userId == :id")
    fun changeUserToken(token: String?, id: Int)

    @Query("SELECT userId FROM users WHERE token == :token")
    fun getUserId(token: String?): Int?
}