package ru.givemesomecoffee.tetamtsandroid.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto


class MovieViewHolder(view: View, private val itemClick: ((String) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val movieTitle: TextView = view.findViewById(R.id.movie_title)
    private val movieDescription: TextView = view.findViewById(R.id.movie_description)
    private val movieCover: ImageView = view.findViewById(R.id.movie_cover)
    private val movieAge: TextView = view.findViewById(R.id.age_sign)
    private val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)

    fun bind(item: MovieDto) {
        movieTitle.text = item.title
        movieDescription.text = item.description
        movieCover.load(item.imageUrl)
        val ageRestriction = item.ageRestriction.toString() + "+"
        movieAge.text = ageRestriction
        val movieRating = item.rateScore
        ratingBar.rating = movieRating.toFloat()
        itemView.setOnClickListener {
            itemClick?.invoke(item.title)
        }
    }
}
