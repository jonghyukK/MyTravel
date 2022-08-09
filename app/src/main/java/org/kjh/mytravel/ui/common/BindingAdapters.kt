package org.kjh.mytravel.ui.common

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.kjh.mytravel.R
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.features.bookmark.BookmarkListAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.place.infowithdaylog.PlaceDayLogListAdapter
import org.kjh.mytravel.ui.features.place.subcity.PlacesBySubCityListAdapter
import org.kjh.mytravel.ui.features.place.subcity.PlacesBySubCityPostListAdapter
import org.kjh.mytravel.ui.features.profile.PostMultipleViewAdapter
import org.kjh.mytravel.ui.features.upload.UploadTempImagesAdapter
import org.kjh.mytravel.ui.features.upload.location.LocationQueryResultAdapter
import org.kjh.mytravel.ui.features.upload.select.MediaStoreImageListAdapter
import org.kjh.mytravel.ui.features.upload.select.SelectedPhotoListAdapter
import org.kjh.mytravel.utils.InputValidator
import org.kjh.mytravel.utils.avoidUncheckedWarnAndCast
import org.kjh.mytravel.utils.navigationHeight
import org.kjh.mytravel.utils.statusBarHeight

/**
 * MyTravel
 * Class: MyBindingAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("fullscreenContainerInsetsWithToolbar")
    fun bindInsetsForFullScreenContainerWithToolbar(
        containerView: View,
        toolbar      : Toolbar
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            containerView.setOnApplyWindowInsetsListener { v, insets ->
                val windowStatusBar = insets.getInsets(WindowInsets.Type.statusBars())
                val windowNavBar    = insets.getInsets(WindowInsets.Type.navigationBars())

                toolbar.updatePadding(top = windowStatusBar.top)
                containerView.updatePadding(bottom = windowNavBar.bottom)
                insets
            }
        } else {
            toolbar.updatePadding(top = containerView.context.statusBarHeight())
            containerView.updatePadding(bottom = containerView.context.navigationHeight())
        }
    }

    @JvmStatic
    @BindingAdapter("toolbarInsets")
    fun Toolbar.bindInsetsForToolbar(value: Boolean) {
        val toolbarHeight = layoutParams.height

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setOnApplyWindowInsetsListener { v, insets ->
                val sysWindow = insets.getInsets(WindowInsets.Type.statusBars())

                v.updateLayoutParams { height = toolbarHeight + sysWindow.top }
                v.updatePadding(top = sysWindow.top)
                insets
            }
        } else {
            val statusBarHeight = context.statusBarHeight()

            updateLayoutParams { height = toolbarHeight + statusBarHeight  }
            updatePadding(top = statusBarHeight)
        }
    }


    @JvmStatic
    @BindingAdapter("toolbarInsets")
    fun bindToolbarInsets(
        motionLayout: MotionLayout,
        toolbar     : Toolbar
    ) {
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
        }
    }

    @JvmStatic
    @BindingAdapter("motionProgress")
    fun bindMotionProgress(view: MotionLayout, progress: Float) {
        view.progress = progress
    }

    @JvmStatic
    @BindingAdapter("bindPostItems")
    fun bindPostItems(view: RecyclerView, items: List<Post>?) {
        val adapter = view.adapter

        (adapter as PlacesBySubCityPostListAdapter).setPostItems(items?: listOf())
    }

    @JvmStatic
    @BindingAdapter("currentState", "stateCallback")
    fun bindBottomSheetState(view: View, state: Int, stateCallback: (Int) -> Unit) {
        val behavior = BottomSheetBehavior.from(view).apply {

            addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED
                        || newState == BottomSheetBehavior.STATE_EXPANDED
                        || newState == STATE_HIDDEN
                    ) {
                        stateCallback(newState)
                    }
                }
            })
        }

        behavior.isHideable = state == STATE_HIDDEN
        behavior.state = state
    }

    @JvmStatic
    @BindingAdapter("onThrottleClick")
    fun View.onThrottleClick(action: () -> Unit) {
        val listener = View.OnClickListener { action() }
        setOnClickListener(OnThrottleClickListener(listener))
    }

    @JvmStatic
    @BindingAdapter("bindOffsetChangedListener")
    fun bindOffsetChangedListener(view: AppBarLayout, callback: ((Boolean) -> Unit)?) {
        var scrollRange = -1

        view.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                callback?.let {
                    it(scrollRange + verticalOffset == 0)
                }
            }
        )
    }

    @JvmStatic
    @BindingAdapter("bindItems")
    fun RecyclerView.bindItems(items: List<*>?) {
        val adapter = this.adapter

        adapter?.let {
            when (adapter) {
                is PostMultipleViewAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Post>())

                is PlaceDayLogListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Post>())

                is MediaStoreImageListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is SelectedPhotoListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is UploadTempImagesAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is BookmarkListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Bookmark>())

                is LocationQueryResultAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<MapQueryItem>())

                is BannerListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Banner>())

                is PlacesBySubCityListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Place>())
            }
        }
    }

    @JvmStatic
    @BindingAdapter("isVisibleMenu")
    fun bindShowMenu(view: Toolbar, isVisible: Boolean) {
        view.menu[0].isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun bindVisible(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("isBookmarked")
    fun bindToolbarMenuIconWithBookmark(
        view        : Toolbar,
        isBookmarked: Boolean
    ) {
        val bookmarkIcon = if (isBookmarked) R.drawable.ic_rank else R.drawable.ic_bookmark
        view.menu[0].setIcon(bookmarkIcon)
    }

    @JvmStatic
    @BindingAdapter("tint")
    fun setIconColorFilter(v: ImageView, colorCode: String) {
        v.setColorFilter(Color.parseColor(colorCode))
    }

    @JvmStatic
    @BindingAdapter("app:onFocusLost")
    fun TextInputEditText.onFocusLost(view: TextInputLayout) {
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                view.error          = null
                view.isErrorEnabled = false
            } else {
                val inputText = text.toString()
                val errorMsgOrNull = InputValidator.getErrorMsgOrNull(this.tag.toString(), inputText)

                view.error = errorMsgOrNull
                view.isErrorEnabled = errorMsgOrNull != null
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:textWithVisible")
    fun bindErrorTextWithVisible(view: TextView, value: String?) {
        view.apply {
            text = value
            isVisible = value != null
        }
    }

    @JvmStatic
    @BindingAdapter("app:imgUrl")
    fun bindImageWithUrl(view: ImageView, imgUrl: String?) {
        imgUrl?.run {
            Glide.with(view)
                .load(imgUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(view)
        } ?: Glide.with(view)
            .load(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(view)
    }
}

