package ru.givemesomecoffee.tetamtsandroid.data.mapper


import android.util.Log
import ru.givemesomecoffee.tetamtsandroid.data.remote.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MoviesApiResponse
import java.lang.Exception

class MoviesMapper {

    fun toMovieUi(response: MoviesApiResponse): List<MovieUi> {
        return response.results.map {
            MovieUi(
                id = it.id.toInt(),
                title = it.title,
                description = it.overview,
                categoryId = 1,
                ageRestriction = "", // getCertification(it.id),
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

}