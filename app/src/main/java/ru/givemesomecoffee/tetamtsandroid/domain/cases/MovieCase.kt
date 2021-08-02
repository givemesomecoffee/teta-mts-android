package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MovieCase {
    private val repository = Repository()

    fun getMovieById(id: Int, restore: Boolean = false): MovieUi {

        val movie = repository.getMovie(id, restore)
        movie.category = repository.getCategoryTitle(movie.categoryId)
        return movie
    }
}