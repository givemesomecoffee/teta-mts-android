package ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Actor
import ru.givemesomecoffee.tetamtsandroid.domain.entity.ActorUi

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val actorPhoto: ImageView = view.findViewById(R.id.actor_cover)
    private val actorName: TextView = view.findViewById(R.id.actor_name)

    fun bind(item: ActorUi) {
        actorPhoto.load(item.imgUrl)
        actorName.text = item.name
    }
}