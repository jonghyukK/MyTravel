package org.kjh.mytravel.ui.features.upload.select

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: SelectPhotoGridLayoutItemDecor
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

class MediaStoreImagesGridDecoration: BaseItemDecoration() {
    
    private val size1 = 1.dpToPx().toInt()

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
            isEdgeLeft -> outRect.right = size1
            isEdgeRight -> outRect.left = size1
            else -> {
                outRect.right = size1 / 2
                outRect.left = size1 / 2
            }
        }

        outRect.bottom = size1
    }
}