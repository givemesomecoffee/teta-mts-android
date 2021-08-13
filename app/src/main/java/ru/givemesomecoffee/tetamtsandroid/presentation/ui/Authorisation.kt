package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase

class Authorisation {
    private val userCase = UserCase()

    fun setNewToken(token: String, id: Int) {
        userCase.changeToken(token, id)
    }

    fun getUserId(token: String): Int?{
        return userCase.getUserId(token)
    }

    fun deleteToken(id: Int){
        userCase.changeToken(null, id)
    }

}