package ru.givemesomecoffee.tetamtsandroid.data.mapper


import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesMapper {

    fun toMovieUi(list: List<Movie>): List<MovieUi> {
        return list.map {
            toMovieUi(it)
        }
    }

    fun toMovieUi(movie: Movie): MovieUi{
        return MovieUi(movie)
    }

}