package ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils

import androidx.recyclerview.widget.DiffUtil
import ru.givemesomecoffee.data.entity.CategoryUi

class CategoriesDiffCallback(
    private val oldList: List<CategoryUi>,
    private val newList: List<CategoryUi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}