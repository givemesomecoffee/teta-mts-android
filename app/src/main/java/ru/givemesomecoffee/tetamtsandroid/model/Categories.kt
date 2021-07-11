package ru.givemesomecoffee.tetamtsandroid.model

import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDatasource

data class Categories(private val categoriesDataSource: MovieCategoriesDatasource) {
    fun getCategories() = categoriesDataSource.getCategories()
}