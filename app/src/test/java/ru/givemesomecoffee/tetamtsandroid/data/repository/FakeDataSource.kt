/*

package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.localdata.LocalDatasource
import ru.givemesomecoffee.localdata.db.entity.*


class FakeDataSource(private val movies: List<MovieDto>, private val categories: List<CategoryDto>) :
    LocalDatasource {
    override fun getAllMovies(): List<MovieDto> {
        return movies
    }

    override fun getMoviesByCategory(categoryId: Int): List<MovieDto> {
        return movies.filter { it.categoryId == categoryId }
    }

    override fun getMovieById(id: Int): MovieWithActors {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): List<CategoryDto> {
        return categories
    }

    override fun getCategoryById(id: Int): CategoryDto {
        return categories.first { it.id == id }
    }

    override fun getUser(id: Int): UserWithFavourites {
        TODO("Not yet implemented")
    }

    override fun checkUser(email: String, password: Int): UserDto {
        TODO("Not yet implemented")
    }
}

*/
