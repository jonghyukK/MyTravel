package org.kjh.mytravel.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: Extensions
 * Created by jonghyukkang on 2022/03/05.
 *
 * Description:
 */

fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Context.navigationHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Int.dpToPx(ctx: Context) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), ctx.resources.displayMetrics).toInt()


fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()




