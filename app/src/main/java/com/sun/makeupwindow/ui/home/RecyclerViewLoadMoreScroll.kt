package com.sun.makeupwindow.ui.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sun.makeupwindow.utlis.ZERO

class RecyclerViewLoadMoreScroll : RecyclerView.OnScrollListener {

    private var visibleThreshold = 5
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var lastVisibleItem: Int = ZERO
    private var totalItemCount: Int = ZERO
    private var layoutManager: RecyclerView.LayoutManager
    var isLoading: Boolean = false

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        totalItemCount = layoutManager.itemCount

        if (layoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions =
                (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions)
        } else if (layoutManager is GridLayoutManager) {
            lastVisibleItem = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            mOnLoadMoreListener.onLoadMore()
            isLoading = true
        }

    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}
