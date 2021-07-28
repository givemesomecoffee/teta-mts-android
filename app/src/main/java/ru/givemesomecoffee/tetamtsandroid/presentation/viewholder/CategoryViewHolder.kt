package ru.givemesomecoffee.tetamtsandroid.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

class CategoryViewHolder(view: View, private val itemClick: ((Int) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val categoryTitle: TextView = view.findViewById(R.id.category_title)

    fun bind(item: CategoryUi) {
        categoryTitle.text = item.title
        itemView.setOnClickListener { this.itemClick?.invoke(item.id) }
    }
}