package ru.givemesomecoffee.tetamtsandroid.data.mapper


import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors
import ru.givemesomecoffee.tetamtsandroid.data.remote.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MoviesApiResponse
import java.lang.Exception

class MoviesMapper {

    private fun toMovieUi(movie: MovieDto): MovieUi {
        return MovieUi(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            categoryId = movie.categoryId,
            ageRestriction = movie.ageRestriction,
            imageUrl = movie.imageUrl,
            rateScore = movie.rateScore
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


    suspend fun toMovieUi(response: MoviesApiResponse): List<MovieUi> {
        return response.results.map {
            MovieUi(
                id = it.id.toInt(),
                title = it.title,
                description = it.overview,
                categoryId = it.genre_ids[0],
                ageRestriction = getCertification(it.id),
                imageUrl = "https://image.tmdb.org/t/p/original" + it.poster_path,
                rateScore = it.vote_average / 2
            )
        }
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

    fun toMovieUi(
        movie: MovieWithActors,
        categoryTitle: String? = null,
        actorsMapper: ActorsMapper
    ): MovieUi {
        return MovieUi(
            id = movie.movie.id,
            title = movie.movie.title,
            description = movie.movie.description,
            categoryId = movie.movie.categoryId,
            ageRestriction = movie.movie.ageRestriction,
            imageUrl = movie.movie.imageUrl,
            rateScore = movie.movie.rateScore,
            category = categoryTitle,
            actors = actorsMapper.toActorUi(movie.actors)
        )
    }

    fun toMovieUi(list: List<MovieDto>): List<MovieUi> {
        return list.map {
            toMovieUi(it)
        }
    }

    fun toMovieDto(movies: List<MovieUi>): List<MovieDto> {
        return movies.map { movie ->
            MovieDto(
                id = movie.id!!,
                title = movie.title,
                description = movie.description,
                categoryId = movie.categoryId,
                ageRestriction = movie.ageRestriction,
                imageUrl = movie.imageUrl,
                rateScore = movie.rateScore
            )
        }
    }

}