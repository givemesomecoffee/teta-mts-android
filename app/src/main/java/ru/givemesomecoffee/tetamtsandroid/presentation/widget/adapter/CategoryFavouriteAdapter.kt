package ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder.CategoryFavouriteViewHolder

class CategoryFavouriteAdapter(
    private var dataset: List<CategoryUi>,
    private var itemClick: ((Int) -> Unit)? = null
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CategoryFavouriteViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.category_item,
                        parent,
                        false
                    ), itemClick)



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
