package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

class LocalDatasourceImpl internal constructor(
    private val moviesDao: MovieDao,
    private val categoriesDao: CategoryDao
) : LocalDatasource {
    override fun getAllMovies(): List<Movie> {
       return  moviesDao.getAll()
    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
      return   moviesDao.getMoviesByCategory(categoryId)
    }

    override fun getMovieById(id: Int): Movie {
       return moviesDao.getMovieById(id)
    }

    override fun setAllMovies(list: List<Movie>) {
        return moviesDao.setAll(list)
    }

    override fun getAllCategories(): List<Category> {
       return categoriesDao.getAll()
    }

    override fun getCategoryById(id: Int): Category {
       return categoriesDao.getCategoryById(id)
    }

    override fun insertAllCategories(list: List<Category>) {
        return categoriesDao.insertAll(list)
    }


}