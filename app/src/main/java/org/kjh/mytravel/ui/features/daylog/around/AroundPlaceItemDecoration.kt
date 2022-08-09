package org.kjh.mytravel.ui.features.daylog.around

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: AroundPlaceItemDecoration
 * Created by jonghyukkang on 2022/08/09.
 *
 * Description:
 */
class AroundPlaceItemDecoration : RecyclerView.ItemDecoration() {
    private val DP = Resources.getSystem().displayMetrics.density

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

        if (viewPosition >= 3) {
            when {
                isLeftItem -> {
                    outRect.right = (DP * 1).toInt()
                }
                isRightItem -> {
                    outRect.left = (DP * 1).toInt()
                }
            }

            outRect.bottom =  (DP * 4).toInt()
        }

    }
}