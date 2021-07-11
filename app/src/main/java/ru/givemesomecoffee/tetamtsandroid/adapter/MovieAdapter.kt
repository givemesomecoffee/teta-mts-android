package ru.givemesomecoffee.tetamtsandroid.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import kotlin.math.roundToInt


class MovieAdapter(
    private val context: Context,
    private val dataset: List<MovieDto>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


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
        var adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieTitle.text = dataset[position].title
        holder.movieDescription.text = dataset[position].description
        holder.movieCover.load(dataset[position].imageUrl)
        val ageRestriction = dataset[position].ageRestriction.toString() + "+"
        holder.movieAge.text = ageRestriction
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


    class RecyclerItemDecoration(private val spanCount: Int, private val spacing: Int = 20) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val childCount = parent.childCount
            /*    var left = 0
                var right = parent.width/2 + 20*/
            val position = parent.getChildAdapterPosition(view)


            val spacing = (55 * parent.context.resources.displayMetrics.density).roundToInt()
            /*val column = position % spanCount*/


            outRect.bottom = spacing


            /*  if (position % 2 != 0){
                  left = 0
                  right = parent.width/2
              }




          *//*    outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount*//*
            outRect.left = left
            outRect.right = right
      *//*      outRect.top = if (position < spanCount) spacing else 0
            outRect.bottom = spacing*/
        }

    }
}