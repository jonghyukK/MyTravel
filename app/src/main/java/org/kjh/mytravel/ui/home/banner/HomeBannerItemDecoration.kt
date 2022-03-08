package org.kjh.mytravel.ui.home.banner

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import kotlin.math.max

/**
 * MyTravel
 * Class: HomeBannerItemDecoration
 * Created by jonghyukkang on 2022/03/05.
 *
 * Description:
 */

class HomeBannerItemDecoration : RecyclerView.ItemDecoration() {
    private val DP = Resources.getSystem().displayMetrics.density

    private val textPaint = Paint().apply {
        textSize = 18 * DP
        color    = Color.WHITE
        isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemTotalCount = parent.adapter?.itemCount ?: 0
        val currentChildPosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (currentChildPosition != RecyclerView.NO_POSITION) {
            val text = "${(currentChildPosition % (itemTotalCount / 2)) + 1}/${itemTotalCount / 2}"

            val padding20 = 20 * DP
            val padding30 = 30 * DP

//            val startX = parent.width - padding30 - textPaint.measureText(text)
//            val startY = parent.height - padding20

            val resourceId = parent.resources.getIdentifier("status_bar_height", "dimen", "android")
            var result = 0
            if (resourceId > 0) {
                result = parent.resources.getDimensionPixelSize(resourceId)
            }



            val startX = parent.width - padding30 - textPaint.measureText(text)
            val startY = (result / 2) * DP + textPaint.measureText(text)

            c.drawText(text, startX, 200f, textPaint)
        }
    }
}