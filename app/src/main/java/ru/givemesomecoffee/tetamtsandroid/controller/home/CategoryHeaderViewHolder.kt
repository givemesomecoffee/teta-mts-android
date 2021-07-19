package ru.givemesomecoffee.tetamtsandroid.controller.home

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R

class CategoryHeaderViewHolder(view: View, private val itemClick: ((Int) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val categoryTitle: TextView = view.findViewById(R.id.category_title)
    fun bind() {
        categoryTitle.setText(R.string.movie_list_category_title)
        categoryTitle.setTextColor(Color.WHITE)
        categoryTitle.setBackgroundResource(R.drawable.category_border_filled)
        itemView.setOnClickListener {
            itemClick?.invoke(0)
        }
    }
}