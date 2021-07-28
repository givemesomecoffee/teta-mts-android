package ru.givemesomecoffee.tetamtsandroid.data.mapper

import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesMapper {

    fun toMovieUi(list: List<MovieDto>): List<MovieUi> {
        return list.map {
            MovieUi(it)
        }
    }
}