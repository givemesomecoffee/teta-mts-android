package ru.givemesomecoffee.tetamtsandroid.domain.entity

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Movie


data class MovieUi(
    val title: String,
    val description: String,
    val rateScore: Float,
    val ageRestriction: String,
    val imageUrl: String,
    val id: Int,
    val categoryId: Int,
    var category: String? = null
)