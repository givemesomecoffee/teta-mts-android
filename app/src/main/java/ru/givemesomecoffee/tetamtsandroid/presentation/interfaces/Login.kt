package ru.givemesomecoffee.tetamtsandroid.presentation.interfaces

interface Login {

    fun showProfile()
    fun exitLogin()
    fun saveLogin(id: Int, token: String)
    fun onRegisterComplete()
    fun getUserId(): Int?
}