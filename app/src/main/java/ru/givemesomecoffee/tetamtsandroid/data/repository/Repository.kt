package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork

class Repository {

    private fun getNewCategoriesDataset(): List<CategoryUi> {
        simulateNetwork()
        return CategoriesMapper().toCategoryUi(MovieCategoriesDataSourceImpl().getCategories())
    }

    private fun getNewMoviesDataset(id: Int = 0): List<MovieUi> {
        simulateNetwork()
        var temp = MoviesMapper().toMovieUi(MoviesDataSourceImpl().getMovies())
        temp = if (id == 0) {
            temp.shuffled().take(5)
        } else {
            temp.filter { it.categoryId == id }
        }
        return temp
    }

    fun getMovie(id: Int): MovieUi {
        simulateNetwork()
      return MoviesMapper().toMovieUi(MoviesDataSourceImpl().getMovies().first{it.id == id})
    }


    fun getCategoriesList(): List<CategoryUi> {
        return  getNewCategoriesDataset()
    }

    fun getMoviesList(id: Int = 0): List<MovieUi> {
        return getNewMoviesDataset(id)
    }

    fun getCategoryTitle(id: Int): String {
        return getCategoriesList().first { it.id == id }.title
    }

}