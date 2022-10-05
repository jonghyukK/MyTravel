package org.kjh.mytravel.ui.features.bookmark

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: BookmarkGridITemDecoration
 * Created by jonghyukkang on 2022/10/04.
 *
 * Description:
 */
class BookmarkGridItemDecoration: BaseItemDecoration() {

    private val size1 = 2.dpToPx().toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)
        val spanCnt = (parent.layoutManager as GridLayoutManager).spanCount

        val isEdgeLeft = viewPosition % spanCnt == 0
        val isEdgeRight = (viewPosition % spanCnt) == (spanCnt - 1)

        when {
            isEdgeLeft -> {
                outRect.right = size1
            }
            isEdgeRight -> {
                outRect.left = size1
            }
        }
    }
}