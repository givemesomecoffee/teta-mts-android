package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import javax.inject.Inject

class MoviesListCases  @Inject constructor(val repository: Repository){


    suspend fun getMoviesList(id: Int?): List<MovieUi> {

        return repository.getMoviesList(id)
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return repository.getCategoriesList()
    }

}