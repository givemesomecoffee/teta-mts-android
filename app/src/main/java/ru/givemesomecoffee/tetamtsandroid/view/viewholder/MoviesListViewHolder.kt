package ru.givemesomecoffee.tetamtsandroid.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto


class MoviesListViewHolder(view: View, private val itemClick: ((Int) -> Unit)?) :
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
        movieAge.text = item.ageRestrictionText
        ratingBar.apply {
            rating = item.rateScore.toFloat()
            contentDescription = item.rateScore.toString()
        }
        itemView.setOnClickListener { itemClick?.invoke(item.id) }
    }
}
