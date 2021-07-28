package ru.givemesomecoffee.tetamtsandroid.domain.entity

import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto

class MovieUi(movieDto: MovieDto) {
    val title: String = movieDto.title
    val description: String = movieDto.description
    val rateScore: Float = movieDto.rateScore.toFloat()
    val ageRestriction: String = movieDto.ageRestriction.toString() + "+"
    val imageUrl: String = movieDto.imageUrl
    val id: Int = movieDto.id
    val categoryId = movieDto.categoryId
    var category: String? = null
}