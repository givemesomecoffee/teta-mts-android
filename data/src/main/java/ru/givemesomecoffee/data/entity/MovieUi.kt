package ru.givemesomecoffee.data.entity

import java.util.*

data class MovieUi(
    val title: String,
    val description: String,
    val rateScore: Float,
    val ageRestriction: String?,
    val imageUrl: String,
    val id: Int?,
    val categoryId: Int,
    var category: String? = null,
    val actors: List<ActorUi>? = null,
    val releaseDate: Date? = null,
    val popularity: Float? = null
)