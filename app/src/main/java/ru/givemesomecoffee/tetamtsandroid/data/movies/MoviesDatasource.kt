package ru.givemesomecoffee.tetamtsandroid.data.movies

import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto

interface MoviesDataSource {
    fun getMovies(): List<MovieDto>
}
