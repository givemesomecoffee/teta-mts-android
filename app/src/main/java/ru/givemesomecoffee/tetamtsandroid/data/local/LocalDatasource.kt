package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.*

interface LocalDatasource {

    fun getAllMovies(): List<MovieDto>

    fun getMoviesByCategory(categoryId: Int): List<MovieDto>

    fun getMovieById(id: Int): MovieWithActors

    fun getAllCategories(): List<CategoryDto>

    fun getCategoryById(id: Int): CategoryDto

    fun getUser(id: Int): UserWithFavourites

    fun checkUser(email: String, password: String): UserDto?

    fun changeUserToken(token: String?, id: Int)

    fun getUserId(token: String?): Int?

    fun saveNewUser(user: UserDto): Int

    fun checkUser(email: String): UserDto?

    fun setFavouriteCategories(categories: List<Int>, id: Int)

    fun saveMovies(movies: List<MovieDto>)

    fun saveCategories(categories: List<CategoryDto>)

    fun saveActors(actors: List<ActorDto>?, id: Int)
}