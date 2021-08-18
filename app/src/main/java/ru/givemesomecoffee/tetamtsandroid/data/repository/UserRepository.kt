package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.UserMapper
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class UserRepository( private val localDatasource: LocalDatasource) {
    private val userMapper by lazy { UserMapper() }
    private val categoriesMapper by lazy { CategoriesMapper() }

    fun getUser(id: Int): UserUi {
        return userMapper.toUserUi(localDatasource.getUser(id), categoriesMapper)
    }

    fun checkUser(email: String, password: String): Int? {
        return localDatasource.checkUser(email, password)?.userId
    }

    fun changeToken(token: String?, id: Int) {
        localDatasource.changeUserToken(token, id)
    }

    fun getUserIdByToken(token: String?): Int? {
        return localDatasource.getUserId(token)
    }

    fun saveNewUser(userUi: UserUi): Int {
        return localDatasource.saveNewUser(userMapper.toUser(userUi))
    }

    fun checkUser(email: String): Int? {
        return localDatasource.checkUser(email)?.userId
    }

    fun setFavouriteCategories(categories: List<Int>, id: Int) {
        localDatasource.setFavouriteCategories(categories, id)
    }
}