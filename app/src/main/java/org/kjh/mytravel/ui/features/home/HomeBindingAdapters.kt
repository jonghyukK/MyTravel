package org.kjh.mytravel.ui.features.home

import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.utils.statusBarHeight

/**
 * MyTravel
 * Class: HomeBindingAdapters
 * Created by jonghyukkang on 2022/07/19.
 *
 * Description:
 */


@BindingAdapter("bindToolbar", "motionProgress")
fun bindHomeMotionScrolling(
    motionLayout: MotionLayout,
    toolbar     : Toolbar,
    progress    : Float
) {
    val toolbarHeight   = toolbar.layoutParams.height
    val statusBarHeight = motionLayout.context.statusBarHeight()
    val wholeToolbarHeight = toolbarHeight + statusBarHeight

    with(motionLayout) {
        constraintSetIds.map { id ->
            updateState(id, getConstraintSet(id).apply {
                constrainHeight(toolbar.id, wholeToolbarHeight)
                toolbar.updatePadding(top = statusBarHeight)
            })
        }

        this.progress = progress
    }
}

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