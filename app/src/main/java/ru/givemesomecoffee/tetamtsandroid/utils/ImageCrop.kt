package ru.givemesomecoffee.tetamtsandroid.utils

import android.widget.ImageView

fun setTopCrop(imageView: ImageView) {
        val matrix = imageView.matrix
        val imageWidth = imageView.drawable.intrinsicWidth
        val screenWidth = imageView.width
        val scaleRatio = screenWidth / imageWidth.toFloat()
        matrix.postScale(scaleRatio, scaleRatio)
        imageView.imageMatrix = matrix
}