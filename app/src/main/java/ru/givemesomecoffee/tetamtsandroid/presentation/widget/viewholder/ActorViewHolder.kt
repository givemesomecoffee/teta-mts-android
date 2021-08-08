package ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Actor

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val actorPhoto: ImageView = view.findViewById(R.id.actor_cover)
    val actorName: TextView = view.findViewById(R.id.actor_name)

    fun bind(item: Actor) {
        actorPhoto.load(item.img)
        actorName.text = item.name
    }
}