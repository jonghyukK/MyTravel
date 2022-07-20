package org.kjh.mytravel.ui.features.home

import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import org.kjh.mytravel.utils.statusBarHeight

/**
 * MyTravel
 * Class: HomeBindingAdapters
 * Created by jonghyukkang on 2022/07/19.
 *
 * Description:
 */


@BindingAdapter("bindToolbar", "motionProgress")
fun bindHomeMotionScrolling(motionLayout: MotionLayout, toolbar: Toolbar, progress: Float) {
    val toolbarHeight   = toolbar.layoutParams.height
    val statusBarHeight = motionLayout.context.statusBarHeight()
    val wholeToolbarHeight = toolbarHeight + statusBarHeight

    with(motionLayout) {
        constraintSetIds.map { id ->
            updateState(id, getConstraintSet(id).apply {
                constrainHeight(toolbar.id, wholeToolbarHeight)
                toolbar.updatePadding(top = statusBarHeight)
            })
        }

        this.progress = progress
    }
}