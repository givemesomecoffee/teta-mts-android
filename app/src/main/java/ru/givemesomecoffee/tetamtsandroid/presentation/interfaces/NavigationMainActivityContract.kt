package ru.givemesomecoffee.tetamtsandroid.presentation.interfaces

interface NavigationMainActivityContract {
    fun init()
    fun recoverFragments()
    fun onMovieCardClicked(id: Int)
    fun onHomeClicked()
    fun onAccountClicked()
    fun onBackPressed()

    interface View{
        fun setHomeActive()
        fun setAccountActive()
    }
}

