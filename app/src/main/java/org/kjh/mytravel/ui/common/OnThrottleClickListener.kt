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
    private val clickListener: View.OnClickListener,
    private val interval     : Long = 400
): View.OnClickListener {

    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed({
                    clickable = true
                }, interval)

                clickListener.onClick(v)
            }
        }
    }
}