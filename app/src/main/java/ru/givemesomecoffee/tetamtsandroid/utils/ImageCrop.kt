package ru.givemesomecoffee.tetamtsandroid.utils

import android.util.Log
import android.widget.ImageView

fun setTopCrop(imageView: ImageView) {
        val matrix = imageView.matrix
        Log.d("matrix", matrix.toString())
        val imageWidth = imageView.drawable.intrinsicWidth
        val screenWidth = imageView.width
        val scaleRatio = screenWidth / imageWidth.toFloat()
        matrix.postScale(scaleRatio, scaleRatio)
        imageView.imageMatrix = matrix
        Log.d("matrix", matrix.toString())
}