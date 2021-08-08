package ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Category
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder.CategoryFavouriteViewHolder
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder.CategoryHeaderViewHolder
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder.CategoryViewHolder
import ru.givemesomecoffee.tetamtsandroid.utils.CategoriesDiffCallback

class CategoryFavouriteAdapter(
    private var dataset: List<Category>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CategoryFavouriteViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.category_item,
                        parent,
                        false
                    ))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {

                is CategoryFavouriteViewHolder -> holder.bind(dataset[position])
            }
        }



        override fun getItemCount(): Int {
            return dataset.size
        }

    }
