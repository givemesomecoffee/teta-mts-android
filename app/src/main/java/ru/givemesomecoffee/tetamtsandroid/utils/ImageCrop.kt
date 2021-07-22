package ru.givemesomecoffee.tetamtsandroid.utils

import android.widget.ImageView

fun setTopCrop(imageView: ImageView) {
    val matrix = imageView.imageMatrix
    val imageWidth = imageView.drawable.intrinsicWidth
    val screenWidth = imageView.resources.displayMetrics.widthPixels
    val scaleRatio = screenWidth / imageWidth.toFloat()
    matrix.postScale(scaleRatio, scaleRatio)
    imageView.imageMatrix = matrix
}