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

        val density = parent.context.resources.displayMetrics.density
        val position = parent.getChildAdapterPosition(view)
        val childCount = state.itemCount
/*        val childCount = state.itemCount
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
    }*/
        if (isMovieList) {
            val rect =  (parent.measuredWidth / 2) - getDp(150, density)

           if (position % 2 != 0) {
/*               outRect.left = getDp(10, density)
              outRect.right = getDp(20, density)*/
              outRect.left = rect/3
                outRect.right = rect - outRect.left
          } else {


              /* outRect.right = getDp(10, density)
               outRect.left = getDp(20, density)*/
               outRect.right = rect/3
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
