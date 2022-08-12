package org.kjh.mytravel.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Patterns
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.kjh.mytravel.BuildConfig
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post
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

fun Fragment.navigateToPlaceInfoWithDayLog(placeName: String) {
    val action = NavGraphDirections.actionGlobalPlaceInfoWithDayLogFragment(placeName)
    navigateTo(action)
}

fun View.navigateToPlaceInfoWithDayLog(placeName: String) {
    val action = NavGraphDirections.actionGlobalPlaceInfoWithDayLogFragment(placeName)
    navigateTo(action)
}

fun Fragment.navigateToDayLogDetail(placeName: String, postId: Int = -1) {
    val action = NavGraphDirections.actionGlobalDayLogDetailFragment(placeName, postId)
    navigateTo(action)
}

fun View.navigateToDayLogDetail(placeName: String, postId: Int = -1) {
    val action = NavGraphDirections.actionGlobalDayLogDetailFragment(placeName, postId)
    navigateTo(action)
}

fun Fragment.navigateTo(action: NavDirections) {
    findNavController().navigate(action)
}

fun View.navigateTo(action: NavDirections) {
    findNavController().navigate(action)
}

inline fun <reified T> List<*>?.avoidUncheckedWarnAndCast(): List<T> =
    this?.filterIsInstance<T>() ?: emptyList()

fun List<Post>.updateBookmarkStateWithPosts(bookmarks: List<Bookmark>) = this.map { post ->
    post.copy(
        isBookmarked = bookmarks.containPlace(post.placeName)
    )
}

fun List<Bookmark>.containPlace(placeName: String) =
    this.find { it.placeName == placeName } != null

fun Fragment.startActivityToSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data   = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        flags  = Intent.FLAG_ACTIVITY_NEW_TASK
    })
}

fun Fragment.hasPermission(): Boolean =
    ContextCompat.checkSelfPermission(
        requireContext(), PERM_READ_EXTERNAL_STORAGE
    ) == PERMISSION_GRANTED




