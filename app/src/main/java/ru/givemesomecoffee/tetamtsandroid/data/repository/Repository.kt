package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.mapper.ActorsMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.UserMapper
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class Repository(
    private val localDatasource: LocalDatasource
) {

    /*feels like this initialisation will be reworked later with tests integration*/
    private val moviesMapper by lazy { MoviesMapper() }
    private val categoriesMapper by lazy { CategoriesMapper() }
    private val userMapper by lazy { UserMapper() }
    private val actorsMapper by lazy { ActorsMapper() }

    private fun getNewCategoriesDataset(): List<CategoryUi> {
        return categoriesMapper.toCategoryUi(localDatasource.getAllCategories())
    }

    private fun getNewMoviesDataset(id: Int?): List<MovieUi> {
        return if (id == null) {
            moviesMapper.toMovieUi(localDatasource.getAllMovies())
        } else {
            moviesMapper.toMovieUi(localDatasource.getMoviesByCategory(id))
        }
    }

    private fun getCategoryTitle(id: Int): String {
        return localDatasource.getCategoryById(id).title
    }

    fun getMovie(id: Int): MovieUi {
        val movie = localDatasource.getMovieById(id)
        val category = getCategoryTitle(movie.movie.categoryId)
        return moviesMapper.toMovieUi(movie, category, actorsMapper)
    }

    fun getCategoriesList(): List<CategoryUi> {
        return getNewCategoriesDataset()
    }

    fun getMoviesList(id: Int?): List<MovieUi> {
        return getNewMoviesDataset(id)
    }

    fun getUser(id: Int): UserUi {
        return userMapper.toUserUi(localDatasource.getUser(id), categoriesMapper)
    }

    fun checkUser(email: String, password: String): Int? {
        return localDatasource.checkUser(email, password)?.userId
    }
}