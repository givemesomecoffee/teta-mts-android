package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import java.lang.Exception

class FakeDataSource(var movies: MutableList<Movie>? = mutableListOf()) : LocalDatasource {
    override fun getAllMovies(): List<Movie> {
        movies?.let { return (it) }
        throw Exception("im null")
    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
        TODO("Not yet implemented")
    }

    override fun getMovieById(id: Int): Movie {
        TODO("Not yet implemented")
    }

    override fun setAllMovies(list: List<Movie>) {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: Int): Category {
        TODO("Not yet implemented")
    }

    override fun insertAllCategories(list: List<Category>) {
        TODO("Not yet implemented")
    }
}