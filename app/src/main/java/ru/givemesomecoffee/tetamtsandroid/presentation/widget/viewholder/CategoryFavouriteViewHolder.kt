package ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi

class CategoryFavouriteViewHolder(view: View, private val itemClick: ((Int) -> Unit)? = null) :
    RecyclerView.ViewHolder(view) {
    private val categoryTitle: TextView = view.findViewById(R.id.category_title)

    fun bind(item: CategoryUi) {
        categoryTitle.setTextColor(Color.BLACK)
        categoryTitle.text = item.title
        if (itemClick != null) {
            categoryTitle.setOnClickListener {
                item.id?.let { it1 -> itemClick.invoke(it1) }
                if (categoryTitle.currentTextColor == Color.BLACK){
                    categoryTitle.setTextColor(Color.WHITE)
                    categoryTitle.setBackgroundResource(R.drawable.round_border_filled)
                } else {
                    categoryTitle.setTextColor(Color.BLACK)
                    categoryTitle.setBackgroundResource(R.drawable.round_border)
                }
            }
        }
    }
}

//TODO: try to change implementations to use 1 adapter and holder for categories and favourites



