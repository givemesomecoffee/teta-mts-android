package ru.givemesomecoffee.tetamtsandroid.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import kotlin.math.roundToInt


class MovieAdapter(
    private val context: Context,
    private val dataset: List<MovieDto>,
    private var itemClick: ((String) -> Unit)?
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

private val inflater: LayoutInflater = LayoutInflater.from(context)

    class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieDescription: TextView = view.findViewById(R.id.movie_description)
        val movieCover: ImageView = view.findViewById(R.id.movie_cover)
        val movieAge: TextView = view.findViewById(R.id.age_sign)

        val rating1: ImageView = view.findViewById(R.id.icon_star)
        val rating2: ImageView = view.findViewById(R.id.icon_star2)
        val rating3: ImageView = view.findViewById(R.id.icon_star3)
        val rating4: ImageView = view.findViewById(R.id.icon_star4)
        val rating5: ImageView = view.findViewById(R.id.icon_star5)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterLayout =
            inflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(adapterLayout)
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
        //TODO: change ratingbar form imageViews to RatingBar?
        val movieRating = dataset[position].rateScore

        if (movieRating > 0) holder.rating1.setImageResource(R.drawable.ic_star_filled)
        if (movieRating > 1) holder.rating2.setImageResource(R.drawable.ic_star_filled)
        if (movieRating > 2) holder.rating3.setImageResource(R.drawable.ic_star_filled)
        if (movieRating > 3) holder.rating4.setImageResource(R.drawable.ic_star_filled)
        if (movieRating > 4) holder.rating5.setImageResource(R.drawable.ic_star_filled)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}