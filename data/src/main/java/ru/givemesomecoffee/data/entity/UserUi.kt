package ru.givemesomecoffee.data.entity

data class UserUi(
    val id: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val phone: Int? = null,
    val favouriteCategories: List<CategoryUi>? = null
)