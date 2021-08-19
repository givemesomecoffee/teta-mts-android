package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.ActorsDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.UserDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.*

class LocalDatasourceImpl(db: AppDatabase) : LocalDatasource {
    private val moviesDao: MovieDao = db.MovieDao()
    private val categoriesDao: CategoryDao = db.CategoryDao()
    private val userDao: UserDao = db.UserDao()
    private val actorDao: ActorsDao = db.ActorsDao()

    override fun getAllMovies(): List<MovieDto> {
        return moviesDao.getAll()
    }

    override fun getMoviesByCategory(categoryId: Int): List<MovieDto> {
        return moviesDao.getMoviesByCategory(categoryId)
    }

    override fun getMovieById(id: Int): MovieWithActors {
        return moviesDao.getMovieById(id)
    }

    override fun getAllCategories(): List<CategoryDto> {
        return categoriesDao.getAll()
    }

    override fun getCategoryById(id: Int): CategoryDto {
        return categoriesDao.getCategoryById(id)
    }

    override fun getUser(id: Int): UserWithFavourites {
        return userDao.getUserData(id)
    }

    override fun checkUser(email: String, password: String): UserDto {
        return userDao.checkUser(email, password)
    }

    override fun checkUser(email: String): UserDto {
        return userDao.checkUser(email)
    }

    override fun changeUserToken(token: String?, id: Int) {
        userDao.changeUserToken(token, id)
    }

    override fun getUserId(token: String?): Int? {
        return userDao.getUserId(token)
    }

    override fun saveNewUser(user: UserDto): Int {
        return userDao.setUser(user).toInt()
    }

    override fun setFavouriteCategories(categories: List<Int>, id: Int) {
        userDao.setUserFavourites(categories.map { UserFavourites(id, it) })
    }

    override fun saveMovies(movies: List<MovieDto>) {
        moviesDao.setAll(movies)
    }

    override fun saveCategories(categories: List<CategoryDto>) {
        categoriesDao.insertAll(categories)
    }

    override fun saveActors(actors: List<ActorDto>?, id: Int) {
        if (actors != null) {
            actorDao.setActors(actors)
            for (actor in actors) {
                actorDao.setActorToMovie(ActorsToMovies(id, actor.id!!))
            }
        }
    }
}