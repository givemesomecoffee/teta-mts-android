package ru.givemesomecoffee.tetamtsandroid.data.assets.categories

import ru.givemesomecoffee.tetamtsandroid.data.entity.Category


interface CategoriesDatasource {
    fun getCategories(): List<Category>
}