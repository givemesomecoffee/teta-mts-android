package ru.givemesomecoffee.tetamtsandroid.domain.cases

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MovieCase {
    fun getMovieById(id: Int): MovieUi {
        val repository = Repository()
        val movie = repository.getMovie(id)
        movie.category = repository.getCategoryTitle(movie.categoryId)
        return movie
    }
}