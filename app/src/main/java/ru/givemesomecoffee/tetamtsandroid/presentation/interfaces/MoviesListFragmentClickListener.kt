package ru.givemesomecoffee.tetamtsandroid.presentation.interfaces

interface MoviesListFragmentClickListener {
    fun onMovieCardClicked(id: Int)
    fun homeOnBackPressed(category: Int)
}