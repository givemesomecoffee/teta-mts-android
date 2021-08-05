package ru.givemesomecoffee.tetamtsandroid.domain.cases

import ru.givemesomecoffee.tetamtsandroid.data.repository.Repository
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesListCases {
    private val repository: Repository = Repository()

    fun getMoviesList(id: Int = 0, restore: Boolean = false): List<MovieUi> {
        return repository.getMoviesList(id, restore)
    }

    fun getCategoriesList(): List<CategoryUi>{
        return repository.getCategoriesList()
    }

}