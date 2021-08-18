package ru.givemesomecoffee.tetamtsandroid.data.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDatasource
import ru.givemesomecoffee.tetamtsandroid.data.mapper.ActorsMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.UserMapper
import ru.givemesomecoffee.tetamtsandroid.data.remote.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MoviesApiResponse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class Repository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: MoviesApiService = MoviesApiService.create()
) {

    /*feels like this initialisation will be reworked later with tests integration*/
    private val moviesMapper by lazy { MoviesMapper() }
    private val categoriesMapper by lazy { CategoriesMapper() }
    private val userMapper by lazy { UserMapper() }
    private val actorsMapper by lazy { ActorsMapper() }

    private suspend fun getNewCategoriesDataset(): List<CategoryUi> {
        return categoriesMapper.toCategoryUi(remoteDatasource.getGenres().genres)
    }

    private fun getNewMoviesDataset(id: Int?): Observable <List<MovieUi>> {
        return if (id == null) {
            // moviesMapper.toMovieUi(remoteDatasource.getMovies())
            return Observable.defer {
                remoteDatasource.getMovies()
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        moviesMapper.toMovieUi(it)
                    }
            }


        } else {
            return Observable.defer {
                remoteDatasource.getMoviesByGenre(genre = id.toString())
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        moviesMapper.toMovieUi(it)
                    }
            }
        }
    }

    suspend fun getMovie(id: Int): MovieUi {
        return moviesMapper.toMovieUi(remoteDatasource.getMovie(id = id.toString()), actorsMapper)
    }

    suspend fun getCategoriesList(): List<CategoryUi> {
        return getNewCategoriesDataset()
    }

    fun getMoviesList(id: Int?): Observable<List<MovieUi>> {
        return getNewMoviesDataset(id)
    }

    fun getUser(id: Int): UserUi {
        return userMapper.toUserUi(localDatasource.getUser(id), categoriesMapper)
    }

    fun checkUser(email: String, password: String): Int? {
        return localDatasource.checkUser(email, password)?.userId
    }

    fun changeToken(token: String?, id: Int) {
        localDatasource.changeUserToken(token, id)
    }

    fun getUserIdByToken(token: String?): Int? {
        return localDatasource.getUserId(token)
    }

    fun saveNewUser(userUi: UserUi): Int {
        return localDatasource.saveNewUser(userMapper.toUser(userUi))
    }

    fun checkUser(email: String): Int? {
        return localDatasource.checkUser(email)?.userId
    }

    fun setFavouriteCategories(categories: List<Int>, id: Int) {
        localDatasource.setFavouriteCategories(categories, id)
    }
}