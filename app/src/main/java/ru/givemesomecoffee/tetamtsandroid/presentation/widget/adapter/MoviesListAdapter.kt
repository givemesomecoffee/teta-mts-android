package ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.data.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils.MoviesDiffCallback
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder.MoviesListViewHolder

class MoviesListAdapter(
    private var dataset: List<MovieUi>,
    private var itemClick: ((Int, ImageView) -> Unit)?
) : RecyclerView.Adapter<MoviesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        return MoviesListViewHolder(adapterLayout, itemClick)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateMoviesList(newList: List<MovieUi>) {
        val callback = MoviesDiffCallback(dataset, newList)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        this.dataset = newList
    }
}