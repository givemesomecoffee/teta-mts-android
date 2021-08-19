package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserDto
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class UserMapper {

    fun toUserUi(user: UserWithFavourites, categoriesMapper: CategoriesMapper): UserUi {
        return UserUi(
            id = user.user.userId,
            name = user.user.name,
            email = user.user.email,
            phone = user.user.phone,
            password = user.user.password,
            favouriteCategories = categoriesMapper.toCategoryUi(user.categories)
        )
    }

    fun toUser(userUi: UserUi): UserDto {
        return UserDto(
            name = userUi.name,
            email = userUi.email,
            password = userUi.password
        )
    }
}