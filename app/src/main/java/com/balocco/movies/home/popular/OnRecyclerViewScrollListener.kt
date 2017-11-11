package com.balocco.movies.home.popular

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

class OnRecyclerViewScrollListener(
        private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    companion object {
        private const val DEFAULT_THRESHOLD = 1
        private const val DEFAULT_VALUE_FOR_LOAD_MORE = 10
    }

    private var visibleThreshold: Int = 0
    private var previousTotalItemCount = 0
    private var loading = true

    // Listener for endless scrolling functionality
    private var endlessScrollListener: OnEndlessListListener? = null

    private val totalItemCount: Int
        get() = layoutManager.itemCount

    private val lastVisibleItem: Int
        get() {
            if (layoutManager is StaggeredGridLayoutManager) {
                val lastPosition = IntArray(3)
                layoutManager.findLastVisibleItemPositions(lastPosition)
                return lastPosition[0]
            } else if (layoutManager is GridLayoutManager) {
                return layoutManager.findLastVisibleItemPosition()
            }

            return (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

    fun setEndlessScrollListener(endlessScrollListener: OnEndlessListListener?) {
        this.endlessScrollListener = endlessScrollListener
        this.visibleThreshold = DEFAULT_THRESHOLD
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (endlessScrollListener != null) {
            handleEndlessScrollListener()
        }
    }

    private fun handleEndlessScrollListener() {
        val totalItemCount = totalItemCount
        val lastVisibleItem = lastVisibleItem

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
            loading = totalItemCount == 0
        }

        // If it is still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it is not currently loading, we check to see if we have breached the visibleThreshold and need to reload
        // more data. If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (timeToLoadMore(totalItemCount, lastVisibleItem)) {
            endlessScrollListener!!.onLoadMore()
            loading = true
        }
    }

    private fun timeToLoadMore(totalItemCount: Int, lastVisibleItem: Int) =
            !loading && totalItemCount > DEFAULT_VALUE_FOR_LOAD_MORE &&
                    totalItemCount - lastVisibleItem <= visibleThreshold
}