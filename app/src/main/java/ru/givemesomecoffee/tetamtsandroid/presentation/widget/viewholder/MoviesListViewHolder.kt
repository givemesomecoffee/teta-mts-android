package ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi


class MoviesListViewHolder(view: View, private val itemClick: ((Int) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val movieTitle: TextView = view.findViewById(R.id.movie_title)
    private val movieDescription: TextView = view.findViewById(R.id.movie_description)
    private val movieCover: ImageView = view.findViewById(R.id.movie_cover)
    private val movieAge: TextView = view.findViewById(R.id.age_sign)
    private val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)

    fun bind(item: MovieUi) {
        movieTitle.text = item.title
        movieDescription.text = item.description
        movieCover.load(item.imageUrl)
        movieAge.text = item.ageRestriction
        if (item.ageRestriction.isEmpty()) {
            movieAge.visibility = View.INVISIBLE
        } else {
            movieAge.visibility = View.VISIBLE
        }
        ratingBar.apply {
            rating = item.rateScore
            contentDescription = item.rateScore.toString()
        }
        itemView.setOnClickListener { item.id?.let { id -> itemClick?.invoke(id) } }
    }
}
