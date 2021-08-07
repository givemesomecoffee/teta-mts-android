package ru.givemesomecoffee.tetamtsandroid.data.repository

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDataSourse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.utils.simulateNetwork
import kotlin.coroutines.coroutineContext

class Repository(
    private val localDataSourse: MoviesDatasource = LocalDataSourse(AppDatabase.getInstance().MovieDao())
    ) {

private suspend fun getNewCategoriesDataset(): List<CategoryUi> {
    Log.d("testcategories", "categories")
    Log.d("testcategories", Thread.currentThread().toString())
    Log.d("testcategories", coroutineContext.toString())
    simulateNetwork()

    return listOf()
    //CategoriesMapper().toCategoryUi(db.CategoryDao().getAll())
}

private fun getNewMoviesDataset(id: Int = 0): List<MovieUi> {
    simulateNetwork()
    return if (id == 0) {
        MoviesMapper().toMovieUi(localDataSourse.getAll())
    } else {
        MoviesMapper().toMovieUi(localDataSourse.getMoviesByCategory(id))
    }
}

fun getMovie(id: Int): MovieUi {
    //  simulateNetwork()
    val movie = MoviesMapper().toMovieUi(localDataSourse.getMovieById(id))
    movie.category = getCategoryTitle(movie.categoryId)
    return movie
}


suspend fun getCategoriesList(): List<CategoryUi> {
    return getNewCategoriesDataset()
}

fun getMoviesList(id: Int = 0): List<MovieUi> {
    return getNewMoviesDataset(id)
}

private fun getCategoryTitle(id: Int): String {
    return " "
    //db.CategoryDao().getCategoryById(id).title
}

}