package ru.givemesomecoffee.remotedata.tmdb.entity

data class MovieApiResponse(
    val id: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Float,
    val credits: Credits,
    val genres: List<Genre>,
)

class Credits(val cast: List<ActorApi>)

data class ActorApi (
    val id: String,
    val name: String,
    val profile_path: String?,
    val order: Int
    )
