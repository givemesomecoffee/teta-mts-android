package ru.givemesomecoffee.tetamtsandroid.model

import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSource

data class Movies(private val moviesDataSource: MoviesDataSource) {
    fun getMovies() = moviesDataSource.getMovies()
    fun geMoviesByCategory(id: Int): List<MovieDto> {
        return moviesDataSource.getMovies().filter { it.categoryId == id }
    }

    fun getMovieById(id: Int): MovieDto {
        return moviesDataSource.getMovies().first { it.id == id }
    }

}