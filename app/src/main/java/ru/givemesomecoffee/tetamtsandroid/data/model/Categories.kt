package ru.givemesomecoffee.tetamtsandroid.data.model

import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDatasource

data class Categories(private val categoriesDataSource: MovieCategoriesDatasource) {
    fun getCategories() = categoriesDataSource.getCategories()
    fun getCategoryById(id: Int) = categoriesDataSource.getCategories().first{it.id == id }
}