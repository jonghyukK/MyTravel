package org.kjh.mytravel.ui.features.home.ranking

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.utils.dpToPx

/**
 * MyTravel
 * Class: PlaceRankingHorizontalItemDecoration
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

private const val DEFAULT_PADDING_20 = 20
private const val DEFAULT_PADDING_0  = 0

class PlaceRankingItemDecoration(
    ctx     : Context,
    left    : Int = DEFAULT_PADDING_20,
    right   : Int = DEFAULT_PADDING_20,
    top     : Int = DEFAULT_PADDING_0,
    bottom  : Int = DEFAULT_PADDING_0
): RecyclerView.ItemDecoration() {

    private val _left   : Int = left.dpToPx(ctx)
    private val _right  : Int = right.dpToPx(ctx)
    private val _top    : Int = top.dpToPx(ctx)
    private val _bottom : Int = bottom.dpToPx(ctx)

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
                outRect.left = _left
                outRect.right = _left / 2
            }
            isEnd -> {
                outRect.right = _right
                outRect.left = _right / 2
            }
            else -> {
                outRect.left = _left / 2
                outRect.right = _right / 2
            }
        }

        outRect.top = _top
        outRect.bottom = _bottom
    }
}