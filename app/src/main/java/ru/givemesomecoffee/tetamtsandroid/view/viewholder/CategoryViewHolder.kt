package ru.givemesomecoffee.tetamtsandroid.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

class CategoryViewHolder(view: View, private val itemClick: ((Int) -> Unit)?) :
    RecyclerView.ViewHolder(view) {
    private val categoryTitle: TextView = view.findViewById(R.id.category_title)

    fun bind(item: CategoryDto) {
        categoryTitle.text = item.title
        itemView.setOnClickListener {
            this.itemClick?.invoke(item.id)
        }
    }
}