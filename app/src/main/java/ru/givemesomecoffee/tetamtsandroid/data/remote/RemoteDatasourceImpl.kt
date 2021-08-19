package ru.givemesomecoffee.tetamtsandroid.data.remote


import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.CertificationResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.GenresResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity.MoviesApiResponse
import javax.inject.Inject

class RemoteDatasourceImpl @Inject constructor(private val apiService: MoviesApiService): RemoteDatasource {

    override suspend fun getMovies(id: String?): MoviesApiResponse {
        return if (id == null){
            apiService.getMovies()
        } else {
            apiService.getMoviesByGenre(genre = id)
        }
    }

    override suspend fun getMovie(id: String): MovieApiResponse {
        return apiService.getMovie(id = id)
    }

    override suspend fun getGenres(): GenresResponse {
        return apiService.getGenres()
    }

    override suspend fun getCertification(id: String): CertificationResponse {
        return apiService.getCert(id = id)
    }
}