package ru.givemesomecoffee.tetamtsandroid.domain.cases

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.App.Companion.repository
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class UserCase {
    fun getUser(id: Int = 0): UserUi {
        Log.d("test", Thread.currentThread().toString())
        return repository.getUser(id)
    }


}