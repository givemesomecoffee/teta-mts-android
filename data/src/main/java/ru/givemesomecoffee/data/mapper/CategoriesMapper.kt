package ru.givemesomecoffee.data.mapper

import ru.givemesomecoffee.data.entity.CategoryUi
import ru.givemesomecoffee.localdata.db.entity.CategoryDto
import ru.givemesomecoffee.remotedata.tmdb.entity.Genre

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
