package ru.givemesomecoffee.tetamtsandroid.domain.entity

data class UserUi(
    val id: Int?,
    val name: String,
    val email: String,
    val password: String,
    val phone: Int?,
    val favouriteCategories: List<CategoryUi>?
)