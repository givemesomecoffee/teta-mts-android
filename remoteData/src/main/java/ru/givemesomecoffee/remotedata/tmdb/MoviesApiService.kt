package ru.givemesomecoffee.remotedata.tmdb

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.givemesomecoffee.remotedata.tmdb.entity.CertificationResponse
import ru.givemesomecoffee.remotedata.tmdb.entity.GenresResponse
import ru.givemesomecoffee.remotedata.tmdb.entity.MovieApiResponse
import ru.givemesomecoffee.remotedata.tmdb.entity.MoviesApiResponse
import java.util.concurrent.TimeUnit

const val API_BASE_URL = "https://api.themoviedb.org/3/"
const val API_LANG_QUERY = "ru"
const val API_TOKEN = "0b60005e258e5e6a053da6f4870cf6bf"
const val API_POPULARITY_ORDER_BY = "popularity.desc"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"

//TODO: fix dependencies to make it internal
interface MoviesApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("sort_by") endpoint: String = API_POPULARITY_ORDER_BY
    ): MoviesApiResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("sort_by") endpoint: String = API_POPULARITY_ORDER_BY,
        @Query("with_genres") genre: String
    ): MoviesApiResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: String,
        @Query("append_to_response") type: String = "credits"
    ): MovieApiResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{id}/release_dates")
    suspend fun getCert(@Path("id") id: String): CertificationResponse

    companion object {

        private fun getOkHttpClient(): OkHttpClient {
            val interceptor = Interceptor { chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter("api_key", API_TOKEN)
                    .addQueryParameter("language", API_LANG_QUERY)
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()
        }

        fun create(): MoviesApiService {
            val moshi = Moshi.Builder()
                .add(CustomDateAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(getOkHttpClient())
                .baseUrl(API_BASE_URL)
                .build()
                .create(MoviesApiService::class.java)
        }
    }

}
