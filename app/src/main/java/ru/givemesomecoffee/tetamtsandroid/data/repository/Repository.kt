package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi


class Repository {
    private var categoriesDataset: List<CategoryUi>? = null
    private var moviesDataset: List<MovieUi>? = null
    private var movieUi: MovieUi? = null
    private val db = AppDatabase.instance

    private fun setNewCategoriesDataset() {
        categoriesDataset =
            CategoriesMapper().toCategoryUi(db.CategoryDao().getAll())
    }

    private fun setNewMoviesDataset(id: Int = 0) {
        moviesDataset = if (id == 0) {
            MoviesMapper().toMovieUi(db.MovieDao().getAll()).shuffled().take(5)
        } else {
            MoviesMapper().toMovieUi(db.MovieDao().getMoviesByCategory(id))
        }
    }

    private fun setMovie(id: Long) {
        movieUi = MoviesMapper().toMovieUi(db.MovieDao().getMovieById(id))
        movieUi!!.category = db.CategoryDao().getCategoryById(movieUi!!.categoryId).title
    }

    fun getCategoriesList(): List<CategoryUi> {
        if (categoriesDataset == null) {
            setNewCategoriesDataset()
        }
        return categoriesDataset!!
    }

    fun getMoviesList(id: Int = 0, restore: Boolean = false): List<MovieUi> {
        if (!restore || moviesDataset == null) {
            setNewMoviesDataset(id)
        }
        return moviesDataset!!
    }

    fun getMovie(id: Long, restore: Boolean = false): MovieUi {
        if (!restore || movieUi == null) {
            setMovie(id)
        }
        return movieUi!!
    }
}