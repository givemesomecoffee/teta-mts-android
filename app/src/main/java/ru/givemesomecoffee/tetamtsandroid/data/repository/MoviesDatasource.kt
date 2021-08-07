package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

interface MoviesDatasource {

    fun getAll(): List<Movie>

    fun getMoviesByCategory(categoryId: Int): List<Movie>

    fun getMovieById(id: Int): Movie

    fun setAll(list: List<Movie>)

}