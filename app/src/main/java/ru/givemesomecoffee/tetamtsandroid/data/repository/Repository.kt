package ru.givemesomecoffee.tetamtsandroid.data.repository

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork
import kotlin.coroutines.coroutineContext

class Repository(
    private val localDatasource: LocalDatasource
) {
    private suspend fun getNewCategoriesDataset(): List<CategoryUi> {
        Log.d("testcategories", "categories")
        Log.d("testcategories", Thread.currentThread().toString())
        Log.d("testcategories", coroutineContext.toString())
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

    fun getMovie(id: Int): MovieUi {
        //  simulateNetwork()
        val movie = MoviesMapper().toMovieUi(localDatasource.getMovieById(id))
        movie.category = getCategoryTitle(movie.categoryId)
        return movie
    }


    suspend fun getCategoriesList(): List<CategoryUi> {
        return getNewCategoriesDataset()
    }

    fun getMoviesList(id: Int = 0): List<MovieUi> {
        return getNewMoviesDataset(id)
    }

    private fun getCategoryTitle(id: Int): String {
        return localDatasource.getCategoryById(id).title
    }

}