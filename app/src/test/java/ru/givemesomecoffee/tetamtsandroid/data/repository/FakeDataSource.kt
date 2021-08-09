
package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites

class FakeDataSource(private val movies: List<Movie>, private val categories: List<Category>) :
    LocalDatasource {
    override fun getAllMovies(): List<Movie> {
        return movies
    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
        return movies.filter { it.categoryId == categoryId }
    }

    override fun getMovieById(id: Int): MovieWithActors {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): List<Category> {
        return categories
    }

    override fun getCategoryById(id: Int): Category {
        return categories.first { it.id == id }
    }

    override fun getUser(id: Int): UserWithFavourites {
        TODO("Not yet implemented")
    }
}

