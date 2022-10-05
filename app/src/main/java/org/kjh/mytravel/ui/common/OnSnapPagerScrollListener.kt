package org.kjh.mytravel.ui.common

import androidx.annotation.NonNull
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: SnapPagerScrollListener
 * Created by jonghyukkang on 2022/05/25.
 *
 * Description:
 */

class OnSnapPagerScrollListener(
    private val snapHelper: PagerSnapHelper,
    private val listener: OnChangeListener
) : RecyclerView.OnScrollListener() {

    interface OnChangeListener {
        fun onSnapped(position: Int)
    }

    private var snapPosition: Int = RecyclerView.NO_POSITION

    // Methods
    override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!hasItemPosition()) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager
            ?: return RecyclerView.NO_POSITION
        val snapView = snapHelper.findSnapView(layoutManager)
            ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(newSnapPosition: Int) {
        if (snapPosition != newSnapPosition) {
           if (hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            }
            snapPosition = newSnapPosition
        }
    }

    private fun hasItemPosition(): Boolean {
        return snapPosition != RecyclerView.NO_POSITION
    }
}