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

class SelectedPhotoItemsDecoration: RecyclerView.ItemDecoration() {

    private val dm = Resources.getSystem().displayMetrics

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewPosition = parent.getChildAdapterPosition(view)

        outRect.top = 5.dpToPx(dm)
        outRect.bottom = 5.dpToPx(dm)
    }
}