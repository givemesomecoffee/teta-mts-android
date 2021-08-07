package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

interface LocalDatasource {

    fun getAllMovies(): List<Movie>

    fun getMoviesByCategory(categoryId: Int): List<Movie>

    fun getMovieById(id: Int): Movie

    fun setAllMovies(list: List<Movie>)

    fun getAllCategories(): List<Category>


    fun getCategoryById(id: Int): Category


    fun insertAllCategories(list: List<Category>)

}