package ru.givemesomecoffee.tetamtsandroid.domain.entity

import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie

class MovieUi(movie: Movie) {
    val title: String = movie.title
    val description: String = movie.description
    val rateScore: Float = movie.rateScore.toFloat()
    val ageRestriction: String = movie.ageRestriction.toString() + "+"
    val imageUrl: String = movie.imageUrl
    val id: Long? = movie.id
    val categoryId = movie.categoryId
    var category: String? = null
    constructor(movie: Movie, categoryTitle: String) : this(movie){
        category = categoryTitle
    }


}