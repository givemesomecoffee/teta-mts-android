package ru.givemesomecoffee.tetamtsandroid.domain.cases

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesListCases {
    private val repository: Repository = App.repository

    fun getMoviesList(id: Int?): List<MovieUi> {
        Log.d("test", Thread.currentThread().toString())
        return repository.getMoviesList(id)
    }

    suspend fun getCategoriesList(): List<CategoryUi>{
        return repository.getCategoriesList()
    }

}