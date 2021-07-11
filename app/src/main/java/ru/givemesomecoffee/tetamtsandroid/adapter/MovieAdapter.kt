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


class MovieAdapter(
    private val context: Context,
    private val dataset: List<MovieDto>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieDescription: TextView = view.findViewById(R.id.movie_description)
        val movieCover: ImageView = view.findViewById(R.id.movie_cover)

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
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    class RecyclerItemDecoration(private val spanCount: Int, private val spacing: Int = 20) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val childCount = parent.childCount
            var left = 0
            var right = parent.width/2 + 20
            val position = parent.getChildAdapterPosition(view)


          val spacing = Math.round(spacing * parent.context.resources.displayMetrics.density)
            val column = position % spanCount



            if (position % 2 != 0){
                left = 0
                right = parent.width/2
            }




        /*    outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount*/
            outRect.left = left
            outRect.right = right
      /*      outRect.top = if (position < spanCount) spacing else 0
            outRect.bottom = spacing*/
        }

    }
}