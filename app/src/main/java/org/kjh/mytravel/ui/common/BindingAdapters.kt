package org.kjh.mytravel.ui.common

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.kjh.mytravel.R
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.features.bookmark.BookmarkItemUiState
import org.kjh.mytravel.ui.features.bookmark.BookmarkListAdapter
import org.kjh.mytravel.ui.features.daylog.DayLogProfileItemUiState
import org.kjh.mytravel.ui.features.daylog.images.DayLogDetailImagesInnerAdapter
import org.kjh.mytravel.ui.features.daylog.profiles.DayLogDetailUserProfilesInnerAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.place.infowithdaylog.PlaceDayLogListAdapter
import org.kjh.mytravel.ui.features.place.subcity.PlacesBySubCityDayLogListAdapter
import org.kjh.mytravel.ui.features.place.subcity.PlacesBySubCityListAdapter
import org.kjh.mytravel.ui.features.profile.DayLogMultipleViewAdapter
import org.kjh.mytravel.ui.features.upload.UploadTempImagesAdapter
import org.kjh.mytravel.ui.features.upload.location.LocationQueryResultAdapter
import org.kjh.mytravel.ui.features.upload.select.MediaStoreImageListAdapter
import org.kjh.mytravel.ui.features.upload.select.SelectedPhotoListAdapter
import org.kjh.mytravel.utils.InputValidator
import org.kjh.mytravel.utils.avoidUncheckedWarnAndCast
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
    @BindingAdapter("marginTopForSumToolbarAndStatusBar")
    fun View.bindMarginWithToolbarAndStatusBarHeight(value: Boolean) {
        val statusBarHeight = context.statusBarHeight()
        val toolBarHeight = resources.getDimension(R.dimen.toolbar_height)

        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = (statusBarHeight + toolBarHeight).toInt()
        }
    }

    @JvmStatic
    @BindingAdapter("setToolbarInset")
    fun Toolbar.bindInsetsForToolbar(value: Boolean) {
        if (!isTopLevelFragment()) {
            setupWithNavController(findNavController())
        }

        val statusBarHeight = context.statusBarHeight()
        val toolbarHeight = layoutParams.height

        updateLayoutParams { height = toolbarHeight + statusBarHeight }
        updatePadding(top = statusBarHeight)
    }

    @JvmStatic
    @BindingAdapter("setToolbarInset")
    fun MotionLayout.bindInsetsForToolbarInMotionLayout(value: Boolean) {
        val childToolBar       = children.filterIsInstance<Toolbar>().toList()[0]
        val childToolbarHeight = childToolBar.layoutParams.height
        val statusBarHeight    = context.statusBarHeight()
        val wholeToolbarHeight = childToolbarHeight + statusBarHeight

        constraintSetIds.map { id ->
            updateState(id, getConstraintSet(id).apply {
                constrainHeight(childToolBar.id, wholeToolbarHeight)
            })
        }
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
        this.adapter?.let {
            when (it) {
                is DayLogMultipleViewAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<DayLog>())

                is PlaceDayLogListAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<DayLog>())

                is PlacesBySubCityDayLogListAdapter ->
                    it.setDayLogItems(items.avoidUncheckedWarnAndCast<DayLog>())

                is MediaStoreImageListAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is SelectedPhotoListAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is UploadTempImagesAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is BookmarkListAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<BookmarkItemUiState>())

                is LocationQueryResultAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<MapQueryItem>())

                is BannerListAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<BannerItemUiState>())

                is PlacesBySubCityListAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<Place>())

                is DayLogDetailImagesInnerAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<String>())

                is DayLogDetailUserProfilesInnerAdapter ->
                    it.submitList(items.avoidUncheckedWarnAndCast<DayLogProfileItemUiState>())
            }
        }
    }

    @JvmStatic
    @BindingAdapter("onTextChanged")
    fun EditText.bindOnTextChanged(onChangedCallback: (String) -> Unit) {
        doOnTextChanged { text, _, _, _ ->
            onChangedCallback(text.toString())
        }
    }

    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun View.bindVisible(isVisible: Boolean = true) {
        this.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("app:onFocusLost")
    fun TextInputEditText.onFocusLost(view: TextInputLayout) {
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                view.error = null
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

private fun View.isTopLevelFragment() =
    findNavController().currentDestination?.id?.let {
        it == R.id.homeFragment
                || it == R.id.bookMarkFragment
                || it == R.id.profileFragment
    } ?: false