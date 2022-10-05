package org.kjh.mytravel.ui.features.profile

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration
import kotlin.math.max

/**
 * MyTravel
 * Class: LineIndicatorDecoration
 * Created by jonghyukkang on 2022/05/23.
 *
 * Description:
 */
class LineIndicatorDecoration: BaseItemDecoration() {

    private val _colorActive = Color.BLACK
    private val _colorInactive = Color.GRAY

    private val _indicatorHeight      = 32.dpToPx().toInt()
    private val _indicatorStrokeWidth = 5.dpToPx()
    private val _indicatorItemLength  = 16.dpToPx().toInt()
    private val _indicatorItemPadding = 10.dpToPx().toInt()

    private val _interpolator = AccelerateDecelerateInterpolator()
    private val _paint = Paint()

    init {
        _paint.apply {
            strokeCap   = Paint.Cap.ROUND
            strokeWidth = _indicatorStrokeWidth
            style       = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter?.itemCount ?: 0

        val totalLength = _indicatorItemLength * itemCount
        val paddingBetweenItems = max(0, itemCount - 1) * _indicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f
        val indicatorPosY = parent.height - _indicatorHeight / 2f

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        val lm = parent.layoutManager as LinearLayoutManager
        val activePosition = lm.findFirstVisibleItemPosition()

        if (activePosition == NO_POSITION) {
            return
        }

        val activeChild = lm.findViewByPosition(activePosition)
        val left = activeChild?.left ?: -1
        val width = activeChild?.width ?: -1

        val progress = _interpolator.getInterpolation(left * -1 / width.toFloat())

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        _paint.color = _colorInactive

        val itemWidth = _indicatorItemLength + _indicatorItemPadding
        var start = indicatorStartX
        for (i in (0 until itemCount)) {
            c.drawLine(start, indicatorPosY, start + _indicatorItemLength, indicatorPosY, _paint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        activePosition: Int,
        progress: Float,
        itemCount: Int
    ) {
        _paint.color = _colorActive

        val itemWidth = _indicatorItemLength + _indicatorItemPadding

        if (progress == 0f) {
            val highlightStart = indicatorStartX + itemWidth * activePosition
            c.drawLine(highlightStart, indicatorPosY, highlightStart + _indicatorItemLength, indicatorPosY, _paint)
        } else {
            var highlightStart = indicatorStartX + itemWidth * activePosition
            val partialLength = _indicatorItemLength * progress

            c.drawLine(highlightStart + partialLength, indicatorPosY, highlightStart + _indicatorItemLength, indicatorPosY, _paint)

            if (activePosition < itemCount - 1) {
                highlightStart += itemWidth
                c.drawLine(highlightStart, indicatorPosY, highlightStart + partialLength, indicatorPosY, _paint)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = _indicatorHeight
    }
}