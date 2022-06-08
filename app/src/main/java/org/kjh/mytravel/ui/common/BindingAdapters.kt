package org.kjh.mytravel.ui.common

import android.animation.ObjectAnimator
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.kjh.mytravel.R
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.features.bookmark.BookmarkListAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.place.detail.PlaceDayLogListAdapter
import org.kjh.mytravel.ui.features.place.subcity.PlacesBySubCityListAdapter
import org.kjh.mytravel.ui.features.profile.PostMultipleViewAdapter
import org.kjh.mytravel.ui.features.upload.MapSearchPlaceListAdapter
import org.kjh.mytravel.ui.features.upload.WritePostImagesAdapter
import org.kjh.mytravel.ui.features.upload.select.MediaStoreImageListAdapter
import org.kjh.mytravel.ui.features.upload.select.SelectedPhotoListAdapter
import org.kjh.mytravel.utils.InputValidator
import org.kjh.mytravel.utils.avoidUncheckedWarnAndCast
import org.kjh.mytravel.utils.dpToPx

/**
 * MyTravel
 * Class: MyBindingAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("isNotEmptySelectedItems", "isDoneAnimated", "setDoneAnimated")
    fun bindAnimatorWithTranslationY(
        view: RecyclerView,
        isNotEmptySelectedItems: Boolean,
        isDoneAnimated: Boolean,
        setDoneAnimated: (Boolean) -> Unit
    ) {
        val translateRange =
            if (isNotEmptySelectedItems)
                90.dpToPx(view.context).toFloat()
            else
                0f

        if (!isDoneAnimated) {
            ObjectAnimator.ofFloat(view, "translationY", translateRange).apply {
                duration = 180
                start()
            }
        } else {
            view.translationY = translateRange
            setDoneAnimated(false)
        }
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

                is WritePostImagesAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<MediaStoreImage>())

                is BookmarkListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Bookmark>())

                is MapSearchPlaceListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<MapQueryItem>())

                is BannerListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Banner>())

                is PlacesBySubCityListAdapter ->
                    adapter.submitList(items.avoidUncheckedWarnAndCast<Place>())
            }
        }
    }

    @JvmStatic
    @BindingAdapter("addOnScrollListener")
    fun bindOnScrollListenerForHomeBanners(view: RecyclerView, bannerCount: Int) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val lm = view.layoutManager as LinearLayoutManager

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstItemVisible = lm.findFirstVisibleItemPosition()

                if (bannerCount != 0) {
                    if (firstItemVisible != 1 && (firstItemVisible % bannerCount == 1)) {
                        lm.scrollToPosition(1)
                    } else if (firstItemVisible == 0) {
                        lm.scrollToPositionWithOffset(
                            bannerCount,
                            -view.computeHorizontalScrollOffset()
                        )
                    }
                }
            }
        })
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
    @BindingAdapter("app:imgUri")
    fun bindImageWithUri(view: ImageView, imgUri: Uri?) {
        imgUri?.run {
            Glide.with(view)
                .load(imgUri)
                .centerCrop()
                .into(view)
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

