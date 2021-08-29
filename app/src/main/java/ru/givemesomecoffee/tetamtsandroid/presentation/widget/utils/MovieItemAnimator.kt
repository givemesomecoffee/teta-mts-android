package ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class MovieItemAnimator: DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }


}