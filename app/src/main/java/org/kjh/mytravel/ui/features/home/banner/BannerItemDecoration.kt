package org.kjh.mytravel.ui.features.home.banner

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: HomeBannerItemDecoration
 * Created by jonghyukkang on 2022/03/05.
 *
 * Description:
 */


class BannerItemDecoration : BaseItemDecoration() {

    // 아래 4변수로 Rect Size 조정, Text는 자동으로 Center.
    private val rectLeftDistance   = 70.dpToPx()
    private val rectRightDistance  = 20.dpToPx()
    private val rectTopDistance    = 50.dpToPx()
    private val rectBottomDistance = 20.dpToPx()

    private val textPaint = Paint().apply {
        textSize  = 16.dpToPx()
        color     = Color.WHITE
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val bgPaint = Paint().apply {
        style       = Paint.Style.FILL_AND_STROKE
        color       = Color.parseColor("#55000000")
        strokeCap   = Paint.Cap.ROUND
        isAntiAlias = true
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        parent.clearOnScrollListeners()
        parent.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            val lm = parent.layoutManager as LinearLayoutManager

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemCount = lm.itemCount / 2
                val firstItemVisible = lm.findFirstVisibleItemPosition()

                if (itemCount != 0) {
                    if (firstItemVisible != 1 && (firstItemVisible % itemCount == 1)) {
                        lm.scrollToPosition(1)
                    } else if (firstItemVisible == 0) {
                        lm.scrollToPositionWithOffset(
                            itemCount,
                            -parent.computeHorizontalScrollOffset()
                        )
                    }
                }
            }
        })
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemTotalCount = parent.adapter?.itemCount ?: 0
        val currentChildPosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (currentChildPosition != RecyclerView.NO_POSITION) {
            val currentPageIndex = (currentChildPosition % (itemTotalCount / 2)) + 1
            val totalCount = itemTotalCount / 2
            val text = "$currentPageIndex/$totalCount"

            val rectLeftPoint   = parent.width - rectLeftDistance
            val rectRightPoint  = parent.width - rectRightDistance
            val rectTopPoint    = parent.height - rectTopDistance
            val rectBottomPoint = parent.height - rectBottomDistance

            val rectF = RectF(rectLeftPoint, rectTopPoint, rectRightPoint, rectBottomPoint)

            // draw RoundRect.
            c.drawRoundRect(rectF, 50f, 50f, bgPaint)

            val bounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, bounds)

            val textXDistance = rectRightDistance + ((rectLeftDistance - rectRightDistance) / 2)
            val textYDistance = rectBottomDistance + ((rectTopDistance - rectBottomDistance) / 2) - (bounds.height() / 2)
            val textXPoint = parent.width - textXDistance
            val textYPoint = parent.height - textYDistance

            // draw Text.
            c.drawText(text, textXPoint, textYPoint, textPaint)
        }
    }
}