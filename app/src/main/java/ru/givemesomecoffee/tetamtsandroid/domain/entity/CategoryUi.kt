package ru.givemesomecoffee.tetamtsandroid.domain.entity

import ru.givemesomecoffee.tetamtsandroid.data.entity.Category

class CategoryUi(category: Category) {
    val id = category.id
    val title = category.title
}