package ru.givemesomecoffee.tetamtsandroid.data.repository

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.mapper.ActorsMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.remote.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import java.lang.Exception

class Repository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: MoviesApiService = MoviesApiService.create()
) {
    /*feels like this initialisation will be reworked later with tests integration*/
    private val moviesMapper by lazy { MoviesMapper() }
    private val categoriesMapper by lazy { CategoriesMapper() }
    private val actorsMapper by lazy { ActorsMapper() }

    private suspend fun getNewCategoriesDataset(): List<CategoryUi> {
        return categoriesMapper.toCategoryUi(remoteDatasource.getGenres().genres)
    }

    private suspend fun getNewMoviesDataset(id: Int?): List<MovieUi> {
        return if (id == null) {
            moviesMapper.toMovieUi(remoteDatasource.getMovies())
        } else {
            moviesMapper.toMovieUi(remoteDatasource.getMoviesByGenre(genre = id.toString()))
        }
    }

    private fun getLocalMoviesDataset(id: Int?): List<MovieUi> {
        return if (id == null) {
            moviesMapper.toMovieUi(localDatasource.getAllMovies())
        } else {
            moviesMapper.toMovieUi(localDatasource.getMoviesByCategory(id))
        }
    }

    private fun getLocalCategoriesList(): List<CategoryUi> {
        return categoriesMapper.toCategoryUi(localDatasource.getAllCategories())
    }

    private fun getCategoryTitle(id: Int): String {
        return localDatasource.getCategoryById(id).title
    }

    suspend fun getMovie(id: Int): MovieUi {
        return try {
            moviesMapper.toMovieUi(
                remoteDatasource.getMovie(id = id.toString()),
                actorsMapper
            )
        } catch (e: Exception) {
            val movie = localDatasource.getMovieById(id = id)
            val category = getCategoryTitle(movie.movie.categoryId)
            moviesMapper.toMovieUi(movie, category, actorsMapper)
        }
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return try {
            getNewCategoriesDataset()
        } catch (e: Exception) {
            getLocalCategoriesList()
        }
    }

    suspend fun getMoviesList(id: Int?): List<MovieUi> {
        return try {
            Log.d("localds", "getNewMoviesDataset(id)")
            getNewMoviesDataset(id)
        } catch (e: Exception) {
            Log.d("localds", e.message.toString())
            getLocalMoviesDataset(id)
        }
    }


}