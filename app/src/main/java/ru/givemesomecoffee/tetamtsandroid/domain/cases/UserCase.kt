package ru.givemesomecoffee.tetamtsandroid.domain.cases

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.App.Companion.repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class UserCase {
    fun getUser(id: Int): UserUi {
        Log.d("test", Thread.currentThread().toString())
        return repository.getUser(id)
    }

    fun checkUser(email:String, password: String): Int?{
        return repository.checkUser(email, password)
    }

}
