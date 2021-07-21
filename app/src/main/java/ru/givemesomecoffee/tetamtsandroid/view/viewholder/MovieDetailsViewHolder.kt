package ru.givemesomecoffee.tetamtsandroid.view.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.imageLoader
import coil.request.ImageRequest
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.categories.MovieCategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.data.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.model.Categories
import ru.givemesomecoffee.tetamtsandroid.model.Movies
import ru.givemesomecoffee.tetamtsandroid.utils.setTopCrop
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment

class MovieDetailsViewHolder(
    val view: View,
    private val movieDetailsClickListener: MovieDetailsFragment.MovieDetailsClickListener?
) {
    private val movieCover: ImageView = view.findViewById(R.id.movie_cover)
    private val backButton: ImageView = view.findViewById(R.id.back_button)
    private val categoryTitle: TextView = view.findViewById(R.id.movie_category)
    private val movieTitle: TextView = view.findViewById(R.id.movie_title)
    private val movieDescription: TextView = view.findViewById(R.id.movie_description)
    private val ageSign: TextView = view.findViewById(R.id.age_sign)

    fun bind(movie: MovieDto) {
        val category =
            Categories(MovieCategoriesDataSourceImpl()).getCategoryById(movie.categoryId)
        val ageRestriction = movie.ageRestriction.toString() + "+"
        backButton.setOnClickListener {
            movieDetailsClickListener?.customOnBackPressed()
        }
        categoryTitle.text = category.title
        movieTitle.text = movie.title
        movieDescription.text = movie.description
        ageSign.text = ageRestriction

        val movieCoverImg = ImageRequest.Builder(view.context)
            .data(movie.imageUrl)
            .target(onSuccess = { result -> setImgToView(movieCover, result) })
            .build()
        view.context.imageLoader.enqueue(movieCoverImg)
    }

    private fun setImgToView(movieCover: ImageView, result: Drawable) {
        movieCover.setImageDrawable(result)
        setTopCrop(movieCover)
    }
}