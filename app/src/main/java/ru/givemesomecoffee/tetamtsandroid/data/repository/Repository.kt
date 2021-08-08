package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork

class Repository(
    private val localDatasource: LocalDatasource
) {
    private suspend fun getNewCategoriesDataset(): List<CategoryUi> {
        simulateNetwork()
        return CategoriesMapper().toCategoryUi(localDatasource.getAllCategories())
    }

    private fun getNewMoviesDataset(id: Int = 0): List<MovieUi> {
        return if (id == 0) {
            MoviesMapper().toMovieUi(localDatasource.getAllMovies())
        } else {
            MoviesMapper().toMovieUi(localDatasource.getMoviesByCategory(id))
        }
    }

    private fun getCategoryTitle(id: Int): String {
        return localDatasource.getCategoryById(id).title
    }

    fun getMovie(id: Int): MovieUi {
        //  simulateNetwork()
        val movie = localDatasource.getMovieById(id)
        val category = getCategoryTitle(movie.categoryId)
        return MoviesMapper().toMovieUi(movie, category)
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return getNewCategoriesDataset()
    }

    fun getMoviesList(id: Int = 0): List<MovieUi> {
        return getNewMoviesDataset(id)
    }



}