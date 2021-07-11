package ru.givemesomecoffee.tetamtsandroid.model

import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSource

data class Movies(private val moviesDataSource: MoviesDataSource) {
    fun getMovies() = moviesDataSource.getMovies()
}