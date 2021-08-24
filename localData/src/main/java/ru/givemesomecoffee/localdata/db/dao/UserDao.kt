package ru.givemesomecoffee.localdata.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.givemesomecoffee.localdata.db.entity.UserDto
import ru.givemesomecoffee.localdata.db.entity.UserFavourites
import ru.givemesomecoffee.localdata.db.entity.UserWithFavourites


@Dao
internal interface UserDao {

    @Transaction
    @Query("SELECT * FROM users WHERE userId == :id")
    fun getUserData(id: Int): UserWithFavourites

    @Query("SELECT * FROM users WHERE email == :email AND password == :password")
    fun checkUser(email: String?, password: String?): UserDto

    @Query("SELECT * FROM users WHERE email == :email")
    fun checkUser(email: String?): UserDto

    @Insert
    fun setUser(user: UserDto): Long

    @Insert
    fun setUserFavourites(favourites: List<UserFavourites>)

    @Query("UPDATE users SET token = :token WHERE userId == :id")
    fun changeUserToken(token: String?, id: Int)

    @Query("SELECT userId FROM users WHERE token == :token")
    fun getUserId(token: String?): Int?
}