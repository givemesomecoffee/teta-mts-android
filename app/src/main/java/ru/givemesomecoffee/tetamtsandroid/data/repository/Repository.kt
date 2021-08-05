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
    private var movieUi: MovieUi? = null

    private fun setNewCategoriesDataset() {
        simulateNetwork()
        categoriesDataset =
            CategoriesMapper().toCategoryUi(MovieCategoriesDataSourceImpl().getCategories())
    }

    private fun setNewMoviesDataset(id: Int = 0) {
        simulateNetwork()
        var temp = MoviesMapper().toMovieUi(MoviesDataSourceImpl().getMovies())
        temp = if (id == 0) {
            temp.shuffled().take(5)
        } else {
            temp.filter { it.categoryId == id }
        }
        moviesDataset = temp
    }

    private fun setMovie(id: Int) {
        simulateNetwork()
        movieUi = MoviesMapper().toMovieUi(MoviesDataSourceImpl().getMovies().first{it.id == id})
    }


    fun getCategoriesList(): List<CategoryUi> {
        if (categoriesDataset == null) {
            setNewCategoriesDataset()
        }
        return categoriesDataset!!
    }

    fun getMoviesList(id: Int = 0, restore: Boolean = false): List<MovieUi> {
     if ( !restore || moviesDataset == null){
         setNewMoviesDataset(id)
     }
        return moviesDataset!!
    }

    fun getMovie(id: Int, restore: Boolean = false): MovieUi {
        if (!restore || movieUi == null) {
            setMovie(id)
        }
        return movieUi!!
    }

    fun getCategoryTitle(id: Int): String {
        if (categoriesDataset != null) {
            return categoriesDataset!!.first { it.id == id }.title
        }
        return getCategoriesList().first { it.id == id }.title
    }

}