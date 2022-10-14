package org.kjh.mytravel.ui.common

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

/**
 * MyTravel
 * Class: OnThrottleMenuItemClickListener
 * Created by jonghyukkang on 2022/05/10.
 *
 * Description:
 */

class OnThrottleMenuItemClickListener(
    private val onClickMenuAction: (MenuItem) -> Unit
): Toolbar.OnMenuItemClickListener {

    private var lastTimeClicked: Long = 0

    private fun isSafe(): Boolean =
        (System.currentTimeMillis() - lastTimeClicked) > CLICK_INTERVAL

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (isSafe()) {
            onClickMenuAction(item)
        }
        lastTimeClicked = System.currentTimeMillis()
        return isSafe()
    }

    companion object {
        const val CLICK_INTERVAL = 500
    }
}

fun Toolbar.setOnThrottleMenuClickListener(action: (MenuItem) -> Unit) {
    val listener = OnThrottleMenuItemClickListener { action(it) }
    setOnMenuItemClickListener(listener)
}