package ru.givemesomecoffee.tetamtsandroid.data.local

import ru.givemesomecoffee.tetamtsandroid.data.dao.MovieDao
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.repository.MoviesDatasource

class LocalDataSourse internal constructor(
    private val moviesDao: MovieDao
) : MoviesDatasource {
    override fun getAll(): List<Movie> {
       return  moviesDao.getAll()
    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
      return   moviesDao.getMoviesByCategory(categoryId)
    }

    override fun getMovieById(id: Int): Movie {
       return moviesDao.getMovieById(id)
    }

    override fun setAll(list: List<Movie>) {
        return moviesDao.setAll(list)
    }


}