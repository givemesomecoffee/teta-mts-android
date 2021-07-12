package ru.givemesomecoffee.tetamtsandroid.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.dto.CategoryDto
import kotlin.math.roundToInt

class CategoryAdapter(
    private val context: Context,
    private val dataset: List<CategoryDto>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class CategoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val categoryTitle: TextView = view.findViewById(R.id.category_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val adapterLayout =
            inflater.inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryTitle.text = dataset[position].title
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}