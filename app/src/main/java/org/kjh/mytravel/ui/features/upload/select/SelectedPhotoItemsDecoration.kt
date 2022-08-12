package org.kjh.mytravel.ui.features.upload.select

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.base.BaseItemDecoration

/**
 * MyTravel
 * Class: SelectPhotoGridLayoutItemDecor
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

class SelectedPhotoItemsDecoration: BaseItemDecoration() {
    
    private val size5 = 5.dpToPx().toInt()
    
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = size5
        outRect.bottom = size5
    }
}