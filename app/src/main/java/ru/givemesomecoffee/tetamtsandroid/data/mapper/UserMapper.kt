package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

object UserMapper {
    fun toUserUi(user: UserWithFavourites): UserUi {
        return UserUi(
            id = user.user.userId,
            name = user.user.name,
            email = user.user.email,
            phone = user.user.phone,
            password = user.user.password,
            favouriteCategories = CategoriesMapper.toCategoryUi(user.categories)
        )
    }
}