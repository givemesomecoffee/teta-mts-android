package ru.givemesomecoffee.tetamtsandroid.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R

class CategoryViewHolder(view: View, itemClick: ((String) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    val categoryTitle: TextView = view.findViewById(R.id.category_title)
}