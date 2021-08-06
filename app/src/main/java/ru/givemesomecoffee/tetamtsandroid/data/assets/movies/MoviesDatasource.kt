package ru.givemesomecoffee.tetamtsandroid.data.assets.movies

import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

interface MoviesDataSource {
    fun getMovies(): List<Movie>
}
