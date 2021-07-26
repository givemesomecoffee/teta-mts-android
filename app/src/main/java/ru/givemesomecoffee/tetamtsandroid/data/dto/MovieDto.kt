package ru.givemesomecoffee.tetamtsandroid.data.dto

data class MovieDto(
    val id: Int,
    val title: String,
    val description: String,
    val rateScore: Int,
    val ageRestriction: Int,
    val imageUrl: String,
    val categoryId: Int
) {
    var categoryTitle: String? = null
    val ageRestrictionText: String = "$ageRestriction+"
}
