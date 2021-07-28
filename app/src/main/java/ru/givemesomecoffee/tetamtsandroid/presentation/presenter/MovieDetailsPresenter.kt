package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MovieDetailsFragment

class MovieDetailsPresenter(val view: MovieDetailsFragment) {
    private val domain: MovieCase = MovieCase()

    fun getMovie(id: Int?) = flow {
        emit(State.LoadingState)
        val movie = domain.getMovieById(id!!)
        emit(State.DataState(movie))
    }.flowOn(Dispatchers.Default)

}