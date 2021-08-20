package ru.givemesomecoffee.tetamtsandroid.data.remote.tmdb.entity

class MoviesApiResponse(val results: List<MovieApi>)

data class MovieApi(
    val id: String,
    val title:String,
    val overview: String,
    val poster_path: String,
    val genre_ids: List<Int>,
    val vote_average: Float
)
