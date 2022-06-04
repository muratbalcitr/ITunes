package com.m2lifeApps.hepsiburada.core.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    /*
      Method gets callback when user scroll the search list
      */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
            firstVisibleItemPosition >= 0
        ) {
            loadMoreItems()
        }
    }

    protected abstract fun loadMoreItems()

    companion object {
        private val TAG = PaginationScrollListener::class.java.simpleName
    }
}
