package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import java.lang.Exception

class FakeDataSource(val movies: List<Movie>, val categories:List<Category>) : LocalDatasource {
    override fun getAllMovies(): List<Movie> {
    return movies

    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
        return movies.filter { it.categoryId == categoryId } ?: throw Exception()
    }

    override fun getMovieById(id: Int): Movie {
        return movies.first { it.id == id } ?: throw Exception()
    }

    override fun getAllCategories(): List<Category> {
        return categories
    }

    override fun getCategoryById(id: Int): Category {
        return categories.first { it.id == id }
    }

}