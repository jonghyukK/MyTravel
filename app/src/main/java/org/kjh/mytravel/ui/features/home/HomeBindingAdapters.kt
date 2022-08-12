package org.kjh.mytravel.ui.features.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerOuterAdapter

/**
 * MyTravel
 * Class: HomeBindingAdapters
 * Created by jonghyukkang on 2022/07/19.
 *
 * Description:
 */


@BindingAdapter("addOnScrollListener")
fun bindOnScrollListenerForHomeBanners(view: RecyclerView, value: Boolean) {
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        val lm = view.layoutManager as LinearLayoutManager

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val itemCount = lm.itemCount / 2
            val firstItemVisible = lm.findFirstVisibleItemPosition()

            if (itemCount != 0) {
                if (firstItemVisible != 1 && (firstItemVisible % itemCount == 1)) {
                    lm.scrollToPosition(1)
                } else if (firstItemVisible == 0) {
                    lm.scrollToPositionWithOffset(
                        itemCount,
                        -view.computeHorizontalScrollOffset()
                    )
                }
            }
        }
    })
}