package ru.givemesomecoffee.tetamtsandroid.utils

import androidx.recyclerview.widget.DiffUtil
import ru.givemesomecoffee.tetamtsandroid.data.dto.MovieDto

class MoviesDiffCallback (private val oldList: List<MovieDto>,
                          private val newList: List<MovieDto>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}