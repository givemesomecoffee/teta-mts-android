package ru.givemesomecoffee.tetamtsandroid.data.mapper


import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesMapper {

    fun toMovieUi(list: List<Movie>): List<MovieUi> {
        return list.map {
            toMovieUi(it)
        }
    }

    private fun toMovieUi(movie: Movie): MovieUi {
        return MovieUi(
            id = movie.id!!,
            title = movie.title,
            description = movie.description,
            categoryId = movie.categoryId,
            ageRestriction = movie.ageRestriction.toString() + "+",
            imageUrl = movie.imageUrl,
            rateScore = movie.rateScore.toFloat()
        )
    }

    fun toMovieUi(movie: MovieWithActors, categoryTitle: String? = null): MovieUi {
        return MovieUi(
            id = movie.movie.id!!,
            title = movie.movie.title,
            description = movie.movie.description,
            categoryId = movie.movie.categoryId,
            ageRestriction = movie.movie.ageRestriction.toString() + "+",
            imageUrl = movie.movie.imageUrl,
            rateScore = movie.movie.rateScore.toFloat(),
            category = categoryTitle,
            actors = movie.actors

        )
    }

}