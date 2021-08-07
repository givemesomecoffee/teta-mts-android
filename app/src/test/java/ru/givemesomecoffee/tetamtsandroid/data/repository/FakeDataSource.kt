package ru.givemesomecoffee.tetamtsandroid.data.repository

import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.LocalDataSourse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import java.lang.Exception
import java.util.ArrayList

class FakeDataSource(var movies: MutableList<Movie>? = mutableListOf()) : MoviesDatasource {
    override fun getAll(): List<Movie> {
        movies?.let { return (it) }
        throw Exception("im null")
    }

    override fun getMoviesByCategory(categoryId: Int): List<Movie> {
        TODO("Not yet implemented")
    }

    override fun getMovieById(id: Int): Movie {
        TODO("Not yet implemented")
    }

    override fun setAll(list: List<Movie>) {
        TODO("Not yet implemented")
    }
}