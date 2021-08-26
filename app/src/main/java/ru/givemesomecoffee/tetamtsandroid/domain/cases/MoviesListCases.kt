package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.data.entity.CategoryUi
import ru.givemesomecoffee.data.entity.MovieUi
import ru.givemesomecoffee.data.repository.Repository
import javax.inject.Inject

class MoviesListCases  @Inject constructor(val repository: Repository){

    suspend fun getMoviesList(id: Int?): List<MovieUi> {

        return repository.getMoviesList(id)
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return repository.getCategoriesList()
    }

    suspend fun refreshMoviesList() {
         repository.refreshMoviesList()
    }

}