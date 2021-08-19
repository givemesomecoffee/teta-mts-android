package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import javax.inject.Inject

class MovieCase @Inject constructor(val repository: Repository) {

    suspend fun getMovieById(id: Int): MovieUi {
        return repository.getMovie(id)
    }
}