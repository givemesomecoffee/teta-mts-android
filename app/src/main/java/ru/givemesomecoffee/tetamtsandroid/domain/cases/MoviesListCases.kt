package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesListCases {
    private val repository: Repository = App.repository

    suspend fun getMoviesList(id: Int?): List<MovieUi> {
        return repository.getMoviesList(id)
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return repository.getCategoriesList()
    }

}