package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.Genre
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

class CategoriesMapper {

    fun toCategoryUi(list: List<Category>): List<CategoryUi> {
        return list.map { CategoryUi(id = it.id, title = it.title) }
    }

    @JvmName("categoryApiToCategoryUi")
    fun toCategoryUi(list: List<Genre>): List<CategoryUi> {
        return list.map {
            CategoryUi(id = it.id, title = it.name)
        }
    }
}
