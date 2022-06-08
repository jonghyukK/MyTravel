package org.kjh.mytravel.ui.features.upload.select

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.utils.dpToPx

/**
 * MyTravel
 * Class: SelectPhotoGridLayoutItemDecor
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

class MediaStoreImagesGridDecoration: RecyclerView.ItemDecoration() {

    private val dm = Resources.getSystem().displayMetrics

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)
        val spanCnt = (parent.layoutManager as GridLayoutManager).spanCount

        val isEdgeLeft = viewPosition % spanCnt == 0
        val isEdgeRight = viewPosition % spanCnt == (spanCnt - 1)

        when {
            isEdgeLeft -> outRect.right = 1.dpToPx(dm)
            isEdgeRight -> outRect.left = 1.dpToPx(dm)
            else -> {
                outRect.right = 1.dpToPx(dm) / 2
                outRect.left = 1.dpToPx(dm) / 2
            }
        }

        outRect.bottom = 1.dpToPx(dm)
    }
}