package ru.givemesomecoffee.tetamtsandroid.data.categories

import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

interface MovieCategoriesDatasource {
    fun getCategories(): List<CategoryDto>
}