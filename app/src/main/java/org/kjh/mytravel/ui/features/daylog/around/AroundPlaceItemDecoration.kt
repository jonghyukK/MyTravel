package org.kjh.mytravel.ui.features.daylog.around

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: AroundPlaceItemDecoration
 * Created by jonghyukkang on 2022/08/09.
 *
 * Description:
 */
class AroundPlaceItemDecoration : BaseItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount

        val isLeftItem = viewPosition % spanCount == 0
        val isRightItem = viewPosition % spanCount == 1

        if (viewPosition == 1) {
            outRect.top = 25.dpToPx().toInt()
        }

        if (viewPosition >= 2) {
            when {
                isLeftItem -> {
                    outRect.right = 1.dpToPx().toInt()
                }
                isRightItem -> {
                    outRect.left = 1.dpToPx().toInt()
                }
            }

            outRect.bottom = 4.dpToPx().toInt()
        }
    }
}