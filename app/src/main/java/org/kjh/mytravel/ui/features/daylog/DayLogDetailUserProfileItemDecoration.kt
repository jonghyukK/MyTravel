package org.kjh.mytravel.ui.features.daylog

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: PlaceDetailUserProfileItemDecoration
 * Created by jonghyukkang on 2022/06/29.
 *
 * Description:
 */
class DayLogDetailUserProfileItemDecoration: RecyclerView.ItemDecoration() {
    private val DP = Resources.getSystem().displayMetrics.density

    private val _int15 = (DP * 15).toInt()
    private val _int20 = (DP * 20).toInt()
    private val _int7 = (DP * 7).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        val isFirstItem = viewPosition == 0
        val isLastItem  = viewPosition == itemCount - 1

        when {
            isFirstItem -> {
                outRect.left = _int15
                outRect.right = _int7
            }
            isLastItem -> {
                outRect.right = _int15
                outRect.left = _int7
            }
            else -> {
                outRect.left = _int7
                outRect.right = _int7
            }
        }

        outRect.top = _int15
        outRect.bottom = _int20
    }
}