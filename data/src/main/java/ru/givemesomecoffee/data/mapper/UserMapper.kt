package ru.givemesomecoffee.data.mapper

import ru.givemesomecoffee.data.entity.UserUi
import ru.givemesomecoffee.localdata.db.entity.UserDto
import ru.givemesomecoffee.localdata.db.entity.UserWithFavourites

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