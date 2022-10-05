package org.kjh.mytravel.ui.features.daylog.profiles

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: PlaceDetailUserProfileItemDecoration
 * Created by jonghyukkang on 2022/06/29.
 *
 * Description:
 */
class DayLogDetailUserProfileItemDecoration: BaseItemDecoration() {

    private val size15 = 15.dpToPx().toInt()
    private val size20 = 20.dpToPx().toInt()
    private val size7 = 7.dpToPx().toInt()

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
                outRect.left = size15
                outRect.right = size7
            }
            isLastItem -> {
                outRect.right = size15
                outRect.left = size7
            }
            else -> {
                outRect.left = size7
                outRect.right = size7
            }
        }
    }
}