package ru.givemesomecoffee.data.repository

import ru.givemesomecoffee.data.entity.UserUi
import ru.givemesomecoffee.data.mapper.CategoriesMapper
import ru.givemesomecoffee.data.mapper.UserMapper
import ru.givemesomecoffee.localdata.LocalDatasource
import javax.inject.Inject

class UserRepository @Inject constructor(private val localDatasource: LocalDatasource) {
    private val userMapper by lazy { UserMapper() }
    private val categoriesMapper by lazy { CategoriesMapper() }

    fun getUser(id: Int): UserUi {
        return userMapper.toUserUi(localDatasource.getUser(id), categoriesMapper)
    }

    fun checkUser(email: String, password: String): Int? {
        return localDatasource.checkUser(email, password)?.userId
    }

    fun checkUser(email: String): Int? {
        return localDatasource.checkUser(email)?.userId
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

    fun setFavouriteCategories(categories: List<Int>, id: Int) {
        localDatasource.setFavouriteCategories(categories, id)
    }
}