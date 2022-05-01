package org.kjh.mytravel.ui.features.place.subcity

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.utils.dpToPx

/**
 * MyTravel
 * Class: PlaceListByCityNameItemDecoration
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityPostItemDecoration: RecyclerView.ItemDecoration() {
    private val dm = Resources.getSystem().displayMetrics

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)

        val isFirst = viewPosition == 0
        val isLast = viewPosition == state.itemCount - 1

        when {
            isFirst -> {
                outRect.left = 20.dpToPx(dm)
                outRect.right = 5.dpToPx(dm)
            }
            isLast -> {
                outRect.left = 5.dpToPx(dm)
                outRect.right = 20.dpToPx(dm)
            }
            else -> {
                outRect.left = 5.dpToPx(dm)
                outRect.right = 5.dpToPx(dm)
            }
        }
    }
}