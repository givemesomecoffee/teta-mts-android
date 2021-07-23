package ru.givemesomecoffee.tetamtsandroid.presenter

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment
import java.lang.Exception


class MoviesListPresenter(private val view: MoviesListFragment) {

    private val categoriesHandler = CoroutineExceptionHandler { _, _ ->
        view.onGetDataFailure("Что-то пошло не так")

        view.viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) { delay(5000L) }
            withContext(Dispatchers.Main) { updateCategories() }

        }
    }
    private val moviesHandler = CoroutineExceptionHandler { _, exception ->
        view.onGetDataFailure(exception.message)
    }
    private var moviesModel: Movies? = null
    private var categoryModel: Categories? = null

    fun updateMoviesListByCategory(categoryId: Int) {
        view.category = categoryId
        view.viewLifecycleOwner.lifecycleScope.launch(moviesHandler) {
            withContext(Dispatchers.Main) { view.setNewMoviesList(getMoviesAsync().await()) }
        }

    }

    private fun getMoviesAsync(): Deferred<List<MovieDto>> {
        return view.viewLifecycleOwner.lifecycleScope.async(moviesHandler) {
            withContext(Dispatchers.IO) {
                getMovies()
            }
        }
    }

    private fun getMovies(): List<MovieDto> {
        if (simulateNetwork() == 500) {
            throw Exception("Ошибка. Попробуйте обновить страницу")
        }
        moviesModel = Movies(MoviesDataSourceImpl())
        return if (view.category == 0) moviesModel!!.getMovies()
        else moviesModel!!.geMoviesByCategory(view.category)
    }

    fun updateCategories() {
        view.viewLifecycleOwner.lifecycleScope.launch(categoriesHandler) {
            withContext(Dispatchers.Main) {
                view.setNewCategoriesList(getCategoriesAsync().await())
                view.categoriesListView.scrollToPosition(0)
            }
        }
    }

    private fun getCategoriesAsync(): Deferred<List<CategoryDto>> {
        return view.viewLifecycleOwner.lifecycleScope.async(categoriesHandler) {
            withContext(Dispatchers.IO) {
                if (simulateNetwork() == 500) {
                    throw Exception("Что-то пошло не так")
                }
                Categories(
                    MovieCategoriesDataSourceImpl()
                ).getCategories()
            }
        }
    }

}