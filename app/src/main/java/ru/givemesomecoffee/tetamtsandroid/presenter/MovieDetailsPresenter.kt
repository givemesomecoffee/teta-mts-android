package ru.givemesomecoffee.tetamtsandroid.presenter

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl

import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import java.lang.Exception

class MovieDetailsPresenter(val view: MovieDetailsFragment) {
    private val movieModel = Movies(MoviesDataSourceImpl())
    private val categoryModel = Categories(MovieCategoriesDataSourceImpl())
    private val handler = CoroutineExceptionHandler { _, exception ->
        view.onGetDataFailure(exception.message)
        view.refreshWrapper?.isRefreshing = false
    }

    fun getSampleResponse(id: Int?) = flow {
        emit(State.LoadingState)
        if (simulateNetwork() == 500) {
            throw Exception("Ошибка. Перезагрузите страницу")
        }
        val movie = movieModel.getMovieById(id!!)
        if (simulateNetwork() == 500) {
            throw Exception("Ошибка. Перезагрузите страницу")
        }
        val category = categoryModel.getCategoryById(movie.categoryId)
        movie.categoryTitle = category.title
        emit(State.DataState(movie))
    }
}