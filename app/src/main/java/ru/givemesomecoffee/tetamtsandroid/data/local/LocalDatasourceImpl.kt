package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.CategoryDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.local.db.AppDatabase
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie

class LocalDatasourceImpl(db: AppDatabase) : LocalDatasource {

    private val moviesDao: MovieDao = db.MovieDao()
    private val categoriesDao: CategoryDao = db.CategoryDao()

    override fun getAllMovies(): List<Movie> {
       return  moviesDao.getAll()
    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
      return   moviesDao.getMoviesByCategory(categoryId)
    }

    override fun getMovieById(id: Int): Movie {
       return moviesDao.getMovieById(id)
    }



    override fun getAllCategories(): List<Category> {
       return categoriesDao.getAll()
    }

    override fun getCategoryById(id: Int): Category {
       return categoriesDao.getCategoryById(id)
    }



}