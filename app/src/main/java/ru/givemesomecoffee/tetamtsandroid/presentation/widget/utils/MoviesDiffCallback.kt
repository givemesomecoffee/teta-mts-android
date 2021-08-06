package ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils

import androidx.recyclerview.widget.DiffUtil
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesDiffCallback(private val oldList: List<MovieUi>,
                         private val newList: List<MovieUi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}