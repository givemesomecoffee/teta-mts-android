package ru.givemesomecoffee.tetamtsandroid.utils

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
        val childCount = state.itemCount
        val position = parent.getChildAdapterPosition(view)
        val density = parent.context.resources.displayMetrics.density

        outRect.right = getDp(spacingRight, density)

        if (position == childCount - 1)
            outRect.right = getDp(spacingEnd, density)

        if (isMovieList && position % 2 != 0) {
            outRect.left = (parent.measuredWidth / 2) - getDp(150, density)
            outRect.right = 0
        }
        outRect.bottom = getDp(spacingBottom, density)
    }

    private fun getDp(spacing: Int, density: Float): Int {
        return (spacing * density).roundToInt()
    }
}
