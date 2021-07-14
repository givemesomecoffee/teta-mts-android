package ru.givemesomecoffee.tetamtsandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.viewholder.CategoryViewHolder
import ru.givemesomecoffee.tetamtsandroid.viewholder.HeaderViewHolder

const val TYPE_CATEGORY = 1
const val TYPE_HEADER = 0

class CategoryAdapter(
    context: Context,
    private val dataset: List<CategoryDto>,
    private val itemClick: ((String) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                inflater.inflate(R.layout.category_item, parent, false),
                itemClick
            )
            TYPE_CATEGORY -> CategoryViewHolder(
                inflater.inflate(
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
            is HeaderViewHolder -> holder.bind()
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