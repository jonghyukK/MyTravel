package org.kjh.mytravel.ui.features.place.subcity

import android.graphics.Rect
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: PlaceListByCityNameItemDecoration
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityDayLogItemDecoration: BaseItemDecoration() {
    
    private val size20 = 20.dpToPx().toInt()
    private val size10 = 10.dpToPx().toInt()
    private val size5  = 5.dpToPx().toInt()
    
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)

        view.updateLayoutParams {
            this.width = parent.width / 3
        }

        val isFirst = viewPosition == 0
        val isLast = viewPosition == state.itemCount - 1

        when {
            isFirst -> {
                outRect.left = size20
                outRect.right = size5
            }
            isLast -> {
                outRect.left = size5
                outRect.right = size20
            }
            else -> {
                outRect.left = size5
                outRect.right = size5
            }
        }

        outRect.top = size10
    }
}