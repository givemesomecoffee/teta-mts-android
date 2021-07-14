package ru.givemesomecoffee.tetamtsandroid.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R


class MovieViewHolder(view: View, itemClick: ((String) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    val movieTitle: TextView = view.findViewById(R.id.movie_title)
    val movieDescription: TextView = view.findViewById(R.id.movie_description)
    val movieCover: ImageView = view.findViewById(R.id.movie_cover)
    val movieAge: TextView = view.findViewById(R.id.age_sign)
    val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
}
