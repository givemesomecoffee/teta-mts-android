package ru.givemesomecoffee.tetamtsandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto


class MovieAdapter(
    context: Context,
    private val dataset: List<MovieDto>,
    private var itemClick: ((String) -> Unit)?
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class MovieViewHolder(view: View, itemClick: ((String) -> Unit)?) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieDescription: TextView = view.findViewById(R.id.movie_description)
        val movieCover: ImageView = view.findViewById(R.id.movie_cover)
        val movieAge: TextView = view.findViewById(R.id.age_sign)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterLayout =
            inflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(adapterLayout, itemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = dataset[position]
        holder.movieTitle.text = item.title
        holder.movieDescription.text = item.description
        holder.movieCover.load(item.imageUrl)
        val ageRestriction = item.ageRestriction.toString() + "+"
        holder.movieAge.text = ageRestriction
        holder.itemView.setOnClickListener {
            itemClick?.invoke(item.title)
        }
        val movieRating = item.rateScore
        holder.ratingBar.rating = movieRating.toFloat()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}