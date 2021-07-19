package ru.givemesomecoffee.tetamtsandroid.controller.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto
import ru.givemesomecoffee.tetamtsandroid.utils.MoviesDiffCallback

class MoviesListAdapter(
    context: Context,
    private var dataset: List<MovieDto>,
    private var itemClick: ((Int) -> Unit)?
) : RecyclerView.Adapter<MoviesListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val adapterLayout =
            inflater.inflate(R.layout.movie_item, parent, false)
        return MoviesListViewHolder(adapterLayout, itemClick)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    fun updateMoviesList(newList: List<MovieDto>) {
        val callback = MoviesDiffCallback(dataset, newList)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        this.dataset = newList
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}