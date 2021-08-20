package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.data.entity.MovieUi
import ru.givemesomecoffee.data.repository.Repository
import javax.inject.Inject

class MovieCase @Inject constructor(val repository: Repository) {

    suspend fun getMovieById(id: Int): MovieUi {
        return repository.getMovie(id)
    }
}