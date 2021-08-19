package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.*

interface LocalDatasource {

    fun getAllMovies(): List<Movie>

    fun getMoviesByCategory(categoryId: Int): List<Movie>

    fun getMovieById(id: Int): MovieWithActors

    fun getAllCategories(): List<Category>

    fun getCategoryById(id: Int): Category

    fun getUser(id: Int): UserWithFavourites

    fun checkUser(email: String, password: String): User?

    fun changeUserToken(token: String?, id: Int)

    fun getUserId(token: String?): Int?

    fun saveNewUser(user: User): Int

    fun checkUser(email: String): User?

    fun setFavouriteCategories(categories: List<Int>, id: Int)

    fun setMovies(movies: List<Movie>)

    fun setCategories(toCategoryDto: List<Category>)

    fun saveActors(actors: List<Actor>?, id: Int)
}