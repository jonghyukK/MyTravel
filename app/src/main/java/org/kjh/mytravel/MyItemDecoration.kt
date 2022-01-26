package org.kjh.mytravel

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

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

class UploadGridLayoutItemDecor(): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)
        val spanCnt = (parent.layoutManager as GridLayoutManager).spanCount

        val column = viewPosition % 3
//        if (viewPosition == 0 || viewPosition == 1 || viewPosition == 2)
//            outRect.top = 10.dpToPx(view.resources.displayMetrics)

        outRect.top = 5

        outRect.left  = column * 5 / 3
        outRect.right = 5 - (column + 1) * 5 / 3

//        if (viewPosition >= 3)
//            outRect.top = 5
    }
}

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

class ProfilePostsGridItemDecoration(val ctx: Context): RecyclerView.ItemDecoration() {
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



class HorizontalSnapDecorationWithCircleIndicator(
    ctx: Context
): RecyclerView.ItemDecoration() {

    private val dp = Resources.getSystem().displayMetrics.density

    private val colorActive = Color.parseColor("#444444")
    private val colorInActive = Color.parseColor("#cecece")

    private val itemStrokeWidth = dp * 3
    private val itemLength      = dp * 3
    private val itemPadding     = dp * 7
    private val itemHeight      = dp * 25
    private val interpolator    = AccelerateDecelerateInterpolator()

    private val circlePaint = Paint().apply {
        strokeWidth = itemStrokeWidth
        style       = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = itemHeight.toInt()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemTotalCount = parent.adapter?.itemCount ?: 0
        val parentLayoutManager = parent.layoutManager as LinearLayoutManager
        val currentChildPosition = parentLayoutManager.findFirstVisibleItemPosition()

        val totalLength = itemStrokeWidth * itemTotalCount
        val paddingItems = max(0, itemTotalCount - 1) * itemPadding
        val indicatorTotalWidth = totalLength + paddingItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f
        val indicatorStartY = parent.height - (10 * dp)

        drawInactiveIndicators(c, indicatorStartX, indicatorStartY, itemTotalCount)

        if (currentChildPosition == RecyclerView.NO_POSITION)
            return

        val activeChild = parentLayoutManager.findViewByPosition(currentChildPosition)
        activeChild?.let {
            val left = activeChild.left
            val width = activeChild.width

            val progress = interpolator.getInterpolation((left * -1) / width.toFloat())

            drawActiveIndicator(c, indicatorStartX, indicatorStartY, currentChildPosition, progress)
        }
    }

    private fun drawActiveIndicator(
        c: Canvas,
        indicatorStartX: Float,
        indicatorStartY: Float,
        activePosition: Int,
        progress: Float
    ) {
        circlePaint.color = colorActive
        val itemWidth = itemStrokeWidth + itemPadding

        if (progress == 0f) {
            val highlightStart = indicatorStartX + itemWidth * activePosition

            c.drawCircle(highlightStart, indicatorStartY, itemLength / 2f, circlePaint)
        } else {
            val highlightStart = indicatorStartX + itemWidth * activePosition
            val partialLength = itemLength * progress + itemPadding * progress

            c.drawCircle(highlightStart + partialLength, indicatorStartY, itemLength / 2f, circlePaint)
        }
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorStartY: Float,
        itemCount: Int
    ) {
        circlePaint.color = colorInActive
        val itemWidth = itemStrokeWidth + itemPadding
        var start = indicatorStartX

        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorStartY, 5f, circlePaint)
            start += itemWidth
        }
    }
}

class LinearLayoutItemDecorationWithTextIndicator(
    ctx: Context,
    left    : Int = DEFAULT_PADDING_20,
    right   : Int = DEFAULT_PADDING_20,
    top     : Int = DEFAULT_PADDING_0,
    bottom  : Int = DEFAULT_PADDING_0
): LinearLayoutItemDecoration(ctx, left, right, top, bottom) {
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

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemTotalCount = parent.adapter?.itemCount ?: 0
        val currentChildPosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

//        val text = "${(currentChildPosition % (itemTotalCount / 2)) + 1}/${itemTotalCount / 2}"

        val text = "3/4"

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

open class LinearLayoutItemDecoration(
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