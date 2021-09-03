package ru.givemesomecoffee.data.repository

import ru.givemesomecoffee.data.entity.CategoryUi
import ru.givemesomecoffee.data.entity.MovieUi
import ru.givemesomecoffee.data.mapper.ActorsMapper
import ru.givemesomecoffee.data.mapper.CategoriesMapper
import ru.givemesomecoffee.data.mapper.MoviesMapper
import ru.givemesomecoffee.localdata.LocalDatasource
import ru.givemesomecoffee.remotedata.RemoteDatasource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {
    /*feels like this initialisation will be reworked later with tests integration*/
    private val moviesMapper by lazy { MoviesMapper() }
    private val categoriesMapper by lazy { CategoriesMapper() }
    private val actorsMapper by lazy { ActorsMapper() }

    private suspend fun getNewCategoriesDataset(): List<CategoryUi> {
        val categories = categoriesMapper.toCategoryUi(remoteDatasource.getGenres().genres)
        localDatasource.saveCategories(categoriesMapper.toCategoryDto(categories))
        return categories
    }

    private suspend fun getNewMoviesDataset(id: Int?): List<MovieUi> {
        val movies = moviesMapper.toMovieUi(remoteDatasource.getMovies(id?.toString()))
        localDatasource.saveMovies(moviesMapper.toMovieDto(movies))
        return movies
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

    private suspend fun getNewMovie(id: Int): MovieUi {
        val movie = moviesMapper.toMovieUi(
            remoteDatasource.getMovie(id = id.toString()),
            actorsMapper
        )
        val actors = movie.actors?.let { actorsMapper.toActorDto(it) }
        localDatasource.saveActors(actors, movie.id!!)
        return movie
    }

    suspend fun getMovie(id: Int): MovieUi {
        return try {
            getNewMovie(id)
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
            getNewMoviesDataset(id)
        } catch (e: Exception) {
            getLocalMoviesDataset(id)
        }
    }

    suspend fun refreshMoviesList() {
        val movies = getNewMoviesDataset(null)
        for (movie in movies) {
            movie.id?.let { getNewMovie(it) }
        }
        getNewCategoriesDataset()
    }

}