package org.kjh.mytravel.ui.base

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: BaseItemDecoration
 * Created by jonghyukkang on 2022/08/13.
 *
 * Description:
 */
open class BaseItemDecoration: RecyclerView.ItemDecoration() {
    private val density = Resources.getSystem().displayMetrics.density

    fun Int.dpToPx() = this * density
}