package org.kjh.mytravel.ui.features.profile.upload

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: SelectPhotoGridLayoutItemDecor
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

class SelectPhotoGridLayoutItemDecor: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)

        val column = viewPosition % 3

        outRect.top = 5
        outRect.left  = column * 5 / 3
        outRect.right = 5 - (column + 1) * 5 / 3
    }
}