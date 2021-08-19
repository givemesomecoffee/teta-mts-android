package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import ru.givemesomecoffee.tetamtsandroid.App

class Authorisation {
    private val userCase = App.appComponent.userCase() //TODO: remove

    fun setNewToken(token: String, id: Int) {
        userCase.changeToken(token, id)
    }

    fun getUserId(token: String): Int? {
        return userCase.getUserId(token)
    }

    fun deleteToken(id: Int) {
        userCase.changeToken(null, id)
    }

}