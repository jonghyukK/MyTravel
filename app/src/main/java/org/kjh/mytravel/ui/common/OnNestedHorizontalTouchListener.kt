package org.kjh.mytravel.ui.common

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * MyTravel
 * Class: OnNestedHorizontalTouchListener
 * Created by jonghyukkang on 2022/08/13.
 *
 * Description:
 */

class OnNestedHorizontalTouchListener: RecyclerView.SimpleOnItemTouchListener() {
    var initialX = 0f
    var initialY = 0f

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_DOWN) {
            rv.parent.requestDisallowInterceptTouchEvent(true)
            initialX = e.rawX
            initialY = e.rawY

        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val xDiff = abs(e.rawX - initialX)
            val yDiff = abs(e.rawY - initialY)
            if (yDiff > xDiff) {
                rv.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onInterceptTouchEvent(rv, e)
    }
}