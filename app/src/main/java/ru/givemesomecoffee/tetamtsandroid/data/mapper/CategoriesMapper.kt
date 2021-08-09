package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

object CategoriesMapper {

    fun toCategoryUi(list: List<Category>): List<CategoryUi> {
        return list.map {
            CategoryUi(id = it.id!!, title = it.title)
        }
    }
}
