package ru.givemesomecoffee.tetamtsandroid.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.CertificationResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.GenresResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MovieApiResponse
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MoviesApiResponse

interface MoviesApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = "0b60005e258e5e6a053da6f4870cf6bf",
        @Query("sort_by") endpoint: String = "popularity.desc",
        @Query("language") lang: String = "ru-RU"
    ): MoviesApiResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String = "0b60005e258e5e6a053da6f4870cf6bf",
        @Query("sort_by") endpoint: String = "popularity.desc",
        @Query("language") lang: String = "ru-RU",
        @Query("with_genres") genre: String
    ): MoviesApiResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String = "0b60005e258e5e6a053da6f4870cf6bf",
        @Query("language") lang: String = "ru-RU",
        @Query("append_to_response") type: String = "credits"
    ): MovieApiResponse

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String = "0b60005e258e5e6a053da6f4870cf6bf",  @Query("language") lang: String = "ru-RU"): GenresResponse

    @GET("movie/{id}/release_dates")
    suspend fun getCert(
        @Path("id") id: String,
        @Query("api_key") apiKey: String = "0b60005e258e5e6a053da6f4870cf6bf"
    ): CertificationResponse

    companion object {
        fun create(): MoviesApiService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("https://api.themoviedb.org/3/")
                .build()
                .create(MoviesApiService::class.java)
        }
    }


}
