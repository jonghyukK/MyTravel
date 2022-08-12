package org.kjh.mytravel.ui.features.home.ranking

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: PlaceRankingHorizontalItemDecoration
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

class PlaceRankingItemDecoration: BaseItemDecoration() {

    private val size20 = 20.dpToPx().toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)

        val isFirst = viewPosition == 0
        val isEnd   = viewPosition == state.itemCount - 1

        when {
            isFirst -> {
                outRect.left = size20
                outRect.right = size20 / 2
            }
            isEnd -> {
                outRect.right = size20
                outRect.left = size20 / 2
            }
            else -> {
                outRect.left = size20 / 2
                outRect.right = size20 / 2
            }
        }
    }
}