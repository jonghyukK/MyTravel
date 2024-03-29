package org.kjh.mytravel.ui.features.daylog

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: FullLineIndicatorDecoration
 * Created by jonghyukkang on 2022/06/29.
 *
 * Description:
 */
class FullLineIndicatorDecoration: BaseItemDecoration() {

    private val _colorActive = Color.BLACK
    private val _colorInactive = Color.LTGRAY

    private val _indicatorHeight      = 5.dpToPx().toInt()
    private val _indicatorStrokeWidth = 5.dpToPx()

    private val _interpolator = AccelerateDecelerateInterpolator()
    private val _paint = Paint()

    init {
        _paint.apply {
            strokeCap   = Paint.Cap.SQUARE
            strokeWidth = _indicatorStrokeWidth
            style       = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val lm = parent.layoutManager as LinearLayoutManager
        val activePosition = lm.findFirstVisibleItemPosition()

        if (activePosition == DiffUtil.DiffResult.NO_POSITION) {
            return
        }

        val itemCount = parent.adapter?.itemCount ?: 0

        val totalLength   = parent.width.toFloat()
        val indicatorPosY = parent.height - (_indicatorHeight / 2f)

        // draw Inactive Indicators..
        drawInactiveIndicators(c, indicatorPosY, totalLength)

        val activeChild = lm.findViewByPosition(activePosition)
        val left = activeChild?.left ?: -1
        val width = activeChild?.width ?: -1

        val progress = _interpolator.getInterpolation(left * -1 / width.toFloat())
        val itemWidth = parent.width / itemCount

        // draw Active Indicator..
        drawActiveIndicator(c, itemWidth, indicatorPosY, activePosition, progress, itemCount)
    }

    private fun drawInactiveIndicators(
        c                  : Canvas,
        indicatorPosY      : Float,
        indicatorTotalWidth: Float
    ) {
        _paint.color = _colorInactive
        c.drawLine(0f, indicatorPosY, indicatorTotalWidth, indicatorPosY, _paint)
    }

    private fun drawActiveIndicator(
        c: Canvas,
        itemWidth: Int,
        indicatorPosY: Float,
        activePosition: Int,
        progress: Float,
        itemCount: Int
    ) {
        _paint.color = _colorActive

        if (progress == 0f) {
            val highlightStart = (itemWidth * activePosition).toFloat()
            c.drawLine(0f, indicatorPosY, highlightStart + itemWidth, indicatorPosY, _paint)
        } else {
            var highlightStart = itemWidth * activePosition
            val partialLength = itemWidth * progress

            c.drawLine(0f, indicatorPosY, (highlightStart + itemWidth).toFloat(), indicatorPosY, _paint)

            if (activePosition < itemCount - 1) {
                highlightStart += itemWidth
                c.drawLine(0f, indicatorPosY, highlightStart + partialLength, indicatorPosY, _paint)
            }
        }
    }
}