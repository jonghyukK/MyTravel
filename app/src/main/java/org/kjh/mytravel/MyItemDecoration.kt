package org.kjh.mytravel

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: MyItemDecoration
 * Created by mac on 2021/12/23.
 *
 * Description:
 */

fun Int.dpToPx(ctx: Context) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), ctx.resources.displayMetrics).toInt()

private const val DEFAULT_PADDING_20 = 20
private const val DEFAULT_PADDING_0  = 0

class GridLayoutItemDecoration(
    ctx     : Context,
    left    : Int = DEFAULT_PADDING_20,
    right   : Int = DEFAULT_PADDING_20,
    top     : Int = DEFAULT_PADDING_20,
    bottom  : Int = DEFAULT_PADDING_20,
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
        val spanCnt = (parent.layoutManager as GridLayoutManager).spanCount

        val isEdgeLeft  = viewPosition % spanCnt == 0
        val isEdgeRight = (viewPosition % spanCnt) == (spanCnt - 1)

        if (viewPosition < spanCnt) {
            outRect.top = _top
        }

        when {
            isEdgeLeft -> {
                outRect.left = _left
                outRect.right = _left / 2
            }
            isEdgeRight -> {
                outRect.right = _right
                outRect.left = _right / 2
            }
            else -> {
                outRect.left = _left / 2
                outRect.right = _right / 2
            }
        }

        outRect.bottom = _bottom
    }
}

class LinearLayoutItemDecoration(
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