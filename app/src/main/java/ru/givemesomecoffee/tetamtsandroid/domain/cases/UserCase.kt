package ru.givemesomecoffee.tetamtsandroid.domain.cases

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.App.Companion.repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class UserCase {
    fun getUser(id: Int): UserUi {
        Log.d("test", Thread.currentThread().toString())
        return repository.getUser(id)
    }

    fun checkUser(email: String, password: String): Int? {
        return repository.checkUser(email, password)
    }

    fun saveNewUser(userUi: UserUi): Int {
        return repository.saveNewUser(userUi)
    }

    fun changeToken(token: String?, id: Int) {
        repository.changeToken(token, id)
    }

    fun getUserId(token: String?): Int? {
        return repository.getUserIdByToken(token)
    }

    fun checkUser(email: String): Int? {
        return repository.checkUser(email)
    }

    fun setFavouriteCategories(categories: List<Int>, id: Int) {
        return repository.setFavouriteCategories(categories, id)
    }

}
