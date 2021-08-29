package ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils

import android.util.Log
import android.widget.ImageView

fun setTopCrop(imageView: ImageView): android.graphics.Matrix {
    Log.d("transitionscale", "im working")
    val matrix = imageView.matrix
    //val imageWidth = result?.intrinsicWidth
    //imageView.drawable.intrinsicWidth
    val imageWidth = imageView.drawable.intrinsicWidth
    Log.d("transitionscale", imageWidth.toString())
    val screenWidth = imageView.resources.displayMetrics.widthPixels

    // val density = imageView.resources.displayMetrics.density

    val scaleRatio = screenWidth.toFloat() / imageWidth.toFloat()
    Log.d("transitionscale", screenWidth.toString())
    Log.d("transitionscale", scaleRatio.toString())
    matrix.postScale(scaleRatio, scaleRatio)
    Log.d("transitionscale", matrix.toString())
    imageView.imageMatrix = null
    imageView.imageMatrix = matrix
    Log.d("transitionscale", imageView.imageMatrix.toString())
    Log.d("transitionscale", "end")
    return matrix
}