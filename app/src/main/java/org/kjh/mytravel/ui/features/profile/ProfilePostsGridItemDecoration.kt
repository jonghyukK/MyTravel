package org.kjh.mytravel.ui.features.profile

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.utils.dpToPx

/**
 * MyTravel
 * Class: ProfilePostsGrildItemDecoration
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */
class ProfilePostsGridItemDecoration(
    private val ctx: Context
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)
        val spanCnt = (parent.layoutManager as GridLayoutManager).spanCount

        val isEdgeLeft  = viewPosition % spanCnt == 0
        val isEdgeRight = (viewPosition % spanCnt) == (spanCnt - 1)

        when {
            isEdgeLeft -> {
                outRect.right = 1.dpToPx(ctx)
            }
            isEdgeRight -> {
                outRect.left = 1.dpToPx(ctx)
            }
        }
    }
}