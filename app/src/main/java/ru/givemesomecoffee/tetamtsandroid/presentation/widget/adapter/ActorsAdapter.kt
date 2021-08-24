package ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.givemesomecoffee.data.entity.ActorUi
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder.ActorViewHolder

class ActorsAdapter(
    private val actors: List<ActorUi>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ActorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.actor_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ActorViewHolder) holder.bind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }
}