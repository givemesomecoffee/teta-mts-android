package ru.givemesomecoffee.tetamtsandroid.utils

import androidx.recyclerview.widget.DiffUtil
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

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