package com.m2lifeApps.hepsiburada.core.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HorizontalLinearLayoutManager(
    context: Context,
    private val widthPercent: Double,
    orientation: Int = 0,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
        lp?.width = (width * widthPercent).toInt()
        return super.checkLayoutParams(lp)
    }
}