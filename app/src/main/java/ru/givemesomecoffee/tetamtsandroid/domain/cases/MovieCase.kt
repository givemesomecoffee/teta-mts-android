package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MovieCase {
    private val repository = App.repository

    suspend fun getMovieById(id: Int): MovieUi {
        return repository.getMovie(id)
    }
}