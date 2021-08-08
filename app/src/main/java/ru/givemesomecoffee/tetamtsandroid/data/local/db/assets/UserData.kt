package ru.givemesomecoffee.tetamtsandroid.data.local.db.assets

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.User
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserFavourites

fun getUser(): User {

    return User(0, "Anatoliy", "test@test.ru", "123456", null)
}

fun getUserFavourites(): List<UserFavourites> {

    val categories: List<Category> = listOf(Category("боевики", 1), Category("фантастика", 6))

    return categories.map { UserFavourites(0, it.id!!) }
}