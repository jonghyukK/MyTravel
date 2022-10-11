package org.kjh.mytravel.ui.features.home

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: HomeBindingAdapters
 * Created by jonghyukkang on 2022/07/19.
 *
 * Description:
 */


@BindingAdapter("onSwipeRefreshAction")
fun SwipeRefreshLayout.bindOnSwipeRefreshListener(refreshAction: () -> Unit) {
    setOnRefreshListener {
        refreshAction()
        isRefreshing = false
    }
}

@BindingAdapter("onScrollAction")
fun RecyclerView.bindOnScrollListener(scrollAction: (Boolean) -> Unit) {
    clearOnScrollListeners()
    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val toolbarHeight = rootView.findViewById<Toolbar>(R.id.tb_toolBar)?.height ?: 0
            val scrollRange = computeVerticalScrollOffset()
            val compareHeight = findViewById<RecyclerView>(R.id.bannerRecyclerView)?.height ?: 0

            scrollAction(scrollRange > compareHeight - toolbarHeight)
        }
    })
}