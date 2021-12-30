package org.kjh.mytravel

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val DP = Resources.getSystem().displayMetrics.density

    private val textPaint = Paint().apply {
        textSize = 11 * DP
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
        isAntiAlias = true
    }

    private val rectPaint = Paint().apply {
        color = Color.BLACK
        setARGB(150, 0, 0, 0)
        isAntiAlias = true
        strokeWidth = 12 * DP
        style = Paint.Style.FILL_AND_STROKE
    }

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

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemTotalCount = parent.adapter?.itemCount ?: 0
        val currentChildPosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        val text = "${(currentChildPosition % (itemTotalCount / 2)) + 1}/${itemTotalCount / 2}"

        val padding20 = 20 * DP
        val padding30 = 30 * DP

        val startX = parent.width - padding30 - textPaint.measureText(text)
        val startY = parent.height - padding20

        val background = getTextBackgroundSize(startX, startY, text, textPaint)
        c.drawRoundRect(background, 50F, 50F, rectPaint)
        c.drawText(text, startX, startY, textPaint)
    }

    private fun getTextBackgroundSize(x: Float, y: Float, text: String, paint: Paint): RectF {
        val fm = paint.fontMetrics
        val halfTextLength = (paint.measureText(text) + (10 * DP)) / 2
        return RectF((x - halfTextLength), (y + fm.top), (x + halfTextLength), (y + fm.bottom))
    }
}