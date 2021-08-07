package ru.givemesomecoffee.tetamtsandroid.data.mapper


import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesMapper {

    fun toMovieUi(list: List<Movie>): List<MovieUi> {
        return list.map {
            toMovieUi(it)
        }
    }

    fun toMovieUi(movie: Movie, categoryTitle: String? = null): MovieUi{
        return MovieUi(
            id = movie.id!!,
            title = movie.title,
            description = movie.description,
            categoryId = movie.categoryId,
            ageRestriction = movie.ageRestriction.toString() + "+",
            imageUrl = movie.imageUrl,
            rateScore = movie.rateScore.toFloat(),
            category = categoryTitle
        )
    }

}