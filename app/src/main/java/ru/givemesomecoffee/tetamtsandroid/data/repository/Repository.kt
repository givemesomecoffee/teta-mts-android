package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork

class Repository {
    private var categoriesDataset: List<CategoryUi>? = null
    private var moviesDataset: List<MovieUi>? = null

    private fun setNewCategoriesDataset() {
        simulateNetwork()
        categoriesDataset =
            CategoriesMapper().toCategoryUi(MovieCategoriesDataSourceImpl().getCategories())
    }

    private fun setNewMoviesDataset() {
        simulateNetwork()
        moviesDataset = MoviesMapper().toMovieUi(MoviesDataSourceImpl().getMovies())
    }

    private fun getAllMoviesList(): List<MovieUi> {
        setNewMoviesDataset()
        return moviesDataset!!
    }

    fun getCategoriesList(): List<CategoryUi> {
        setNewCategoriesDataset()
        return categoriesDataset!!
    }

    fun getMoviesList(id: Int): List<MovieUi> {
        setNewMoviesDataset()
        return if (id == 0) {
            moviesDataset!!.shuffled().take(5)
        } else {
            moviesDataset!!.filter { it.categoryId == id }
        }
    }

    fun getMovie(id: Int): MovieUi {
        return getAllMoviesList().first { it.id == id }
    }

    fun getCategoryTitle(id: Int): String {
        if (categoriesDataset != null){
            return categoriesDataset!!.first{it.id == id}.title
        }
        return getCategoriesList().first { it.id == id }.title
    }


}