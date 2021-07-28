package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

class CategoriesMapper() {

    fun toCategoryUi(list: List<CategoryDto>): List<CategoryUi> {
        return list.map{
            CategoryUi(it)
        }
    }
}