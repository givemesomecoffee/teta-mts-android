package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesMapper {

    fun toMovieUi(list: List<MovieDto>): List<MovieUi> {
        return list.map {
            toMovieUi(it)
        }
    }

    fun toMovieUi(movie: MovieDto): MovieUi{
        return MovieUi(movie)
    }
}