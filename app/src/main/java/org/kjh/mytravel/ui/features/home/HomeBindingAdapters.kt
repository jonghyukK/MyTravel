package org.kjh.mytravel.ui.features.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: HomeBindingAdapters
 * Created by jonghyukkang on 2022/07/19.
 *
 * Description:
 */


@BindingAdapter("addOnScrollListener")
fun bindOnScrollListenerForHomeBanners(view: RecyclerView, bannerCount: Int) {
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        val lm = view.layoutManager as LinearLayoutManager

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val firstItemVisible = lm.findFirstVisibleItemPosition()

            if (bannerCount != 0) {
                if (firstItemVisible != 1 && (firstItemVisible % bannerCount == 1)) {
                    lm.scrollToPosition(1)
                } else if (firstItemVisible == 0) {
                    lm.scrollToPositionWithOffset(
                        bannerCount,
                        -view.computeHorizontalScrollOffset()
                    )
                }
            }
        }
    })
}