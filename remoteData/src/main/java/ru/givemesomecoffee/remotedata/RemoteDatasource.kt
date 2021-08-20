package ru.givemesomecoffee.remotedata

import ru.givemesomecoffee.remotedata.tmdb.entity.CertificationResponse
import ru.givemesomecoffee.remotedata.tmdb.entity.GenresResponse
import ru.givemesomecoffee.remotedata.tmdb.entity.MovieApiResponse
import ru.givemesomecoffee.remotedata.tmdb.entity.MoviesApiResponse

interface RemoteDatasource {

    suspend fun getMovies(id: String?): MoviesApiResponse

    suspend fun getMovie(id: String): MovieApiResponse

    suspend fun getGenres(): GenresResponse

    suspend fun getCertification(id: String): CertificationResponse

}