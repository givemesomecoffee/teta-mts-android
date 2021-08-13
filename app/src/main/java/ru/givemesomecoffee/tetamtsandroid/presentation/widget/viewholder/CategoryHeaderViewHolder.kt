package ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R

class CategoryHeaderViewHolder(view: View, private val itemClick: ((Int?) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val categoryTitle: TextView = view.findViewById(R.id.category_title)

    fun bind() {
        categoryTitle.setText(R.string.movie_list_category_title)
        itemView.setOnClickListener { itemClick?.invoke(null) }
    }
}