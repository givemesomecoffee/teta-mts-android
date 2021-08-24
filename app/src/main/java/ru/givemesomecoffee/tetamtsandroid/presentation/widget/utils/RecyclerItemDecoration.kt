package ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class RecyclerItemDecoration(
    private val spacingRight: Int = 0,
    private val spacingBottom: Int = 0,
    private val spacingEnd: Int = 0,
    private val isMovieList: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val density = parent.context.resources.displayMetrics.density
        val position = parent.getChildAdapterPosition(view)
        val childCount = state.itemCount

        if (isMovieList) {
            val rect = (parent.measuredWidth / 2) - getDp(150, density)
            if (position % 2 != 0) {
                outRect.left = rect / 3
                outRect.right = rect - outRect.left
            } else {
                outRect.right = rect / 3
                outRect.left = rect - outRect.right
            }
            outRect.bottom = getDp(spacingBottom, density)
        } else {
            outRect.right = getDp(spacingRight, density)
            if (position == childCount - 1)
                outRect.right = getDp(spacingEnd, density)
        }
    }

    private fun getDp(spacing: Int, density: Float): Int {
        return (spacing * density).roundToInt()
    }

}
