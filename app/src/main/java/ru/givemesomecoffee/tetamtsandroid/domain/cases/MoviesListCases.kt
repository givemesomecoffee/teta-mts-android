package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import java.util.*
import io.reactivex.Observable

class MoviesListCases {
    private val repository: Repository = App.repository

    fun getMoviesList(id: Int?): Observable<List<MovieUi>> {
        return Observable.defer { repository.getMoviesList(id) }
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return repository.getCategoriesList()
    }

}