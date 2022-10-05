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
    private val action  : (MenuItem) -> Unit,
    private val interval: Long = 400
): Toolbar.OnMenuItemClickListener {

    private var lastTimeClicked: Long = 0

    override fun onMenuItemClick(item: MenuItem): Boolean {
        val recentClickTime = lastTimeClicked
        lastTimeClicked = System.currentTimeMillis()

        return if (System.currentTimeMillis() - recentClickTime < interval) {
            false
        } else {
            action(item)
            true
        }
    }
}