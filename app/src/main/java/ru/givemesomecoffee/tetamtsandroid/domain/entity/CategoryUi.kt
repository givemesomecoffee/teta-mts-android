package ru.givemesomecoffee.tetamtsandroid.domain.entity

import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

class CategoryUi(category: CategoryDto) {
    val id = category.id
    val title = category.title
}