package ru.givemesomecoffee.tetamtsandroid.data.remote

import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.CertificationResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.GenresResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.MoviesApiResponse

interface RemoteDatasource {

    suspend fun getMovies(id: String?): MoviesApiResponse

    suspend fun getMovie(id: String): MovieApiResponse

    suspend fun getGenres(): GenresResponse

    suspend fun getCertification(id: String): CertificationResponse

}