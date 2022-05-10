package org.kjh.mytravel.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Patterns
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.OnThrottleClickListener
import org.kjh.mytravel.ui.common.OnThrottleMenuItemClickListener

/**
 * MyTravel
 * Class: Extensions
 * Created by jonghyukkang on 2022/03/05.
 *
 * Description:
 */

// todo : Extension 종류가 많아질 경우, ViewExtension, CollectionExtension 등 File 분리 필요.

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

fun String.isValidPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun View.onThrottleClick(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun Toolbar.onThrottleMenuItemClick(action: (MenuItem) -> Unit) {
    val menuClickListener = OnThrottleMenuItemClickListener(action = { action(it) })
    setOnMenuItemClickListener(menuClickListener)
}

fun Fragment.navigatePlaceDetailByPlaceName(placeName: String) {
    val action = NavGraphDirections.actionGlobalPlacePagerFragment(placeName)
    navigateWithAction(action)
}

fun Fragment.navigateWithAction(action: NavDirections) {
    findNavController().navigate(action)
}




