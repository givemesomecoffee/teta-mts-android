package ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils

import android.util.Log
import android.widget.ImageView

fun setTopCrop(imageView: ImageView) {
    Log.d("transitionscale", "im working")
    val matrix = android.graphics.Matrix()
    Log.d("transitionscale", matrix.toString())
    val imageWidth = imageView.drawable.intrinsicWidth
    Log.d("transitionscale", imageWidth.toString())
    val screenWidth = imageView.resources.displayMetrics.widthPixels

   // val density = imageView.resources.displayMetrics.density

    val scaleRatio = screenWidth  / imageWidth.toFloat()
    Log.d("transitionscale", screenWidth.toString())
    Log.d("transitionscale", scaleRatio.toString())
    Log.d("transitionscale", imageView.imageMatrix.toString())
    matrix.postScale(scaleRatio, scaleRatio)
    imageView.imageMatrix = matrix
    Log.d("transitionscale", imageView.imageMatrix.toString())
}