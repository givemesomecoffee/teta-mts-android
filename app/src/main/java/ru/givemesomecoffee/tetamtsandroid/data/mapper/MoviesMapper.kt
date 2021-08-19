package ru.givemesomecoffee.tetamtsandroid.data.mapper

import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.MovieWithActors
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.IMAGE_BASE_URL
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.Certification
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.MoviesApiResponse
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

    private suspend fun getCertification(id: String): Certification? {
        val cert = MoviesApiService.create().getCert(id = id)
        var certification: Certification? = null
        try {
            val dates = cert.results.first { it.iso_3166_1 == "RU" }
            certification = dates.release_dates[0]
        } catch (e: Exception) {
            Log.d("retro", cert.results.toString())
        }
        return certification
    } //TODO: should be implemented not here


    suspend fun toMovieUi(response: MoviesApiResponse): List<MovieUi> {
        return response.results.map {
            val certification = getCertification(it.id)
            MovieUi(
                id = it.id.toInt(),
                title = it.title,
                description = it.overview,
                categoryId = it.genre_ids[0],
                ageRestriction = certification?.certification,
                imageUrl = IMAGE_BASE_URL + it.poster_path,
                rateScore = it.vote_average / 2
            )
        }
    }

    suspend fun toMovieUi(movie: MovieApiResponse, actorsMapper: ActorsMapper): MovieUi {
        val certification = getCertification(movie.id)
        return MovieUi(
            title = movie.title,
            description = movie.overview,
            id = movie.id.toInt(),
            rateScore = movie.vote_average / 2,
            ageRestriction = certification?.certification,
            imageUrl = IMAGE_BASE_URL + movie.poster_path,
            actors = actorsMapper.toActorUi(movie.credits.cast),
            categoryId = movie.genres[0].id,
            category = movie.genres[0].name,
            releaseDate = certification?.release_date

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
            actors = actorsMapper.toActorUi(movie.actors),
            releaseDate = movie.movie.releaseDate
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
                rateScore = movie.rateScore,
                releaseDate = movie.releaseDate
            )
        }
    }

}