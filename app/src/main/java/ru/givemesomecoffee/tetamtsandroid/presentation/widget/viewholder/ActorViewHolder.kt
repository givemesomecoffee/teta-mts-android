package ru.givemesomecoffee.tetamtsandroid.presentation.widget.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.givemesomecoffee.data.entity.ActorUi
import ru.givemesomecoffee.tetamtsandroid.R

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val actorPhoto: ImageView = view.findViewById(R.id.actor_cover)
    private val actorName: TextView = view.findViewById(R.id.actor_name)
    private val progressBar: ProgressBar = view.findViewById(R.id.actor_img_progress_bar)

    private fun setImg(it: Drawable) {
        progressBar.visibility = View.INVISIBLE
        actorPhoto.setImageDrawable(it)
    }

    private fun setLoading() {
        progressBar.visibility = View.VISIBLE
        actorPhoto.setImageDrawable(null)
    }

    fun bind(item: ActorUi) {
        if (item.imgUrl.isNullOrEmpty()) {
            actorPhoto.setImageResource(R.drawable.no_image)
            progressBar.visibility = View.INVISIBLE
        } else {
            actorPhoto.load(item.imgUrl) {
                error(R.drawable.no_image)
                target(
                    onSuccess = { setImg(it) },
                    onError = { setImg(it!!) },
                    onStart = { setLoading() }
                )
            }
        }
        actorName.text = item.name
    }

}