package ru.givemesomecoffee.tetamtsandroid.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto

const val TYPE_CATEGORY = 1
const val TYPE_HEADER = 0

class CategoryAdapter(
    context: Context,
    private val dataset: List<CategoryDto>,
    private var itemClick: ((String) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class CategoryViewHolder(view: View, itemClick: ((String) -> Unit)?) :
        RecyclerView.ViewHolder(view) {
        val categoryTitle: TextView = view.findViewById(R.id.category_title)
    }

    class HeaderViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val categoryTitle: TextView = view.findViewById(R.id.category_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(inflater.inflate(R.layout.category_item, parent, false))
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
            is HeaderViewHolder -> holder.apply {
                categoryTitle.setText(R.string.movie_list_category_title)
                categoryTitle.setTextColor(Color.WHITE)
                categoryTitle.setBackgroundResource(R.drawable.category_border_filled)
            }
            is CategoryViewHolder -> holder.apply {
                val item = dataset[position - 1]
                categoryTitle.text = item.title
                itemView.setOnClickListener {
                    itemClick?.invoke(item.title)
                }
            }
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