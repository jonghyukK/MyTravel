package org.kjh.mytravel.ui.common

import android.view.View

/**
 * MyTravel
 * Class: OnThrottleClickListener
 * Created by jonghyukkang on 2022/05/10.
 *
 * Description:
 */
class OnThrottleClickListener(
    private val onClickAction: (View) -> Unit
): View.OnClickListener {

    private var lastTimeClicked: Long = 0

    private fun isSafe(): Boolean =
        (System.currentTimeMillis() - lastTimeClicked) > CLICK_INTERVAL

    override fun onClick(v: View?) {
        if (isSafe() && v != null) {
            onClickAction(v)
        }
        lastTimeClicked = System.currentTimeMillis()
    }

    companion object {
        const val CLICK_INTERVAL = 500
    }
}

fun View.setOnThrottleClickListener(action: (View) -> Unit) {
    val listener = OnThrottleClickListener { action(it) }
    setOnClickListener(listener)
}
