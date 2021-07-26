package ru.givemesomecoffee.tetamtsandroid.presenter

import kotlinx.coroutines.flow.flow
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import java.lang.Exception

class MovieDetailsPresenter(val view: MovieDetailsFragment) {

    fun getMovie(id: Int?) = flow {
        emit(State.LoadingState)
        if (simulateNetwork() == 500) {
            throw Exception("Ошибка. Перезагрузите страницу")
        }
        val movie = Movies(MoviesDataSourceImpl()).getMovieById(id!!)
        if (simulateNetwork() == 500) {
            throw Exception("Ошибка. Перезагрузите страницу")
        }
        val category = Categories(MovieCategoriesDataSourceImpl()).getCategoryById(movie.categoryId)
        movie.categoryTitle = category.title
        emit(State.DataState(movie))
    }
}