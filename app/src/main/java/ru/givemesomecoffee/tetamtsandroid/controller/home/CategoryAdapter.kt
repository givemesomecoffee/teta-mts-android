package ru.givemesomecoffee.tetamtsandroid.controller.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

const val TYPE_CATEGORY = 1
const val TYPE_HEADER = 0

class CategoryAdapter(
    private val dataset: List<CategoryDto>,
    private val itemClick: ((Int) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> CategoryHeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false),
                itemClick
            )
            TYPE_CATEGORY -> CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.category_item,
                    parent,
                    false
                ), itemClick
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryHeaderViewHolder -> holder.bind()
            is CategoryViewHolder -> holder.bind(dataset[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_CATEGORY
        }
    }

    override fun getItemCount(): Int {
        return dataset.size + 1
    }
}