package ru.givemesomecoffee.tetamtsandroid.data.mapper


import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors
import ru.givemesomecoffee.tetamtsandroid.data.remote.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MoviesApiResponse
import java.lang.Exception

class MoviesMapper {

    suspend fun toMovieUi(response: MoviesApiResponse): List<MovieUi> {
        return response.results.map {
            MovieUi(
                id = it.id.toInt(),
                title = it.title,
                description = it.overview,
                categoryId = 1,
                ageRestriction = getCertification(it.id),
                imageUrl = "https://image.tmdb.org/t/p/original" + it.poster_path,
                rateScore = it.vote_average / 2
            )
        }
    }

    fun toMovieUi(
        movie: MovieWithActors,
        categoryTitle: String? = null,
        actorsMapper: ActorsMapper
    ): MovieUi {
        return MovieUi(
            id = movie.movie.id!!,
            title = movie.movie.title,
            description = movie.movie.description,
            categoryId = movie.movie.categoryId,
            ageRestriction = movie.movie.ageRestriction.toString() + "+",
            imageUrl = movie.movie.imageUrl,
            rateScore = movie.movie.rateScore.toFloat(),
            category = categoryTitle,
            actors = actorsMapper.toActorUi(movie.actors)
        )
    }


    suspend fun toMovieUi(movie: MovieApiResponse, actorsMapper: ActorsMapper): MovieUi {
        return MovieUi(
            title = movie.title,
            description = movie.overview,
            id = movie.id.toInt(),
            rateScore = movie.vote_average / 2,
            ageRestriction = getCertification(movie.id),
            imageUrl = "https://image.tmdb.org/t/p/original" + movie.poster_path,
            actors = actorsMapper.toActorUi(movie.credits.cast),
            categoryId = movie.genres[0].id,
            category = movie.genres[0].name
        )
    }

    private suspend fun getCertification(id: String): String {
        val cert = MoviesApiService.create().getCert(id = id)
        var certification = ""
        try {
            val dates = cert.results.first { it.iso_3166_1 == "RU" }
            certification = dates.release_dates[0].certification
        } catch (e: Exception) {
            Log.d("retro", cert.results.toString())
        }
        return certification
    } //TODO: should be implemented not here

    fun toMovieUi(list: List<Movie>): List<MovieUi> {
        return list.map {
            toMovieUi(it)
        }
    }

    private fun toMovieUi(movie: Movie): MovieUi {
        return MovieUi(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            categoryId = movie.categoryId,
            ageRestriction = movie.ageRestriction.toString() + "+",
            imageUrl = movie.imageUrl,
            rateScore = movie.rateScore.toFloat()
        )
    }

}