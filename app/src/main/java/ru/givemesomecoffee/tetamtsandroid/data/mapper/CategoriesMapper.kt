package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.Genre
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

class CategoriesMapper {

    fun toCategoryUi(list: List<CategoryDto>): List<CategoryUi> {
        return list.map { CategoryUi(id = it.id, title = it.title) }
    }

    @JvmName("categoryApiToCategoryUi")
    fun toCategoryUi(list: List<Genre>): List<CategoryUi> {
        return list.map {
            CategoryUi(id = it.id, title = it.name)
        }
    }

    fun toCategoryDto(categories: List<CategoryUi>): List<CategoryDto> {
        return categories.map {
            CategoryDto(
                id = it.id,
                title = it.title
            )
        }
    }
}
