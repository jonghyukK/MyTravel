package org.kjh.mytravel.ui.common

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.features.bookmark.BookmarkListAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingDataAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.ui.features.place.PlaceDayLogListAdapter
import org.kjh.mytravel.ui.features.profile.MyPostListAdapter
import org.kjh.mytravel.ui.features.profile.upload.MapSearchPlaceListAdapter
import org.kjh.mytravel.ui.features.profile.upload.MediaStoreImageListAdapter
import org.kjh.mytravel.ui.features.profile.upload.SelectedPhotoListAdapter
import org.kjh.mytravel.ui.features.profile.upload.WritePostImagesAdapter
import org.kjh.mytravel.ui.features.user.UserPostListAdapter
import org.kjh.mytravel.ui.features.profile.InputValidator
import org.kjh.mytravel.ui.features.profile.InputValidator.INPUT_TYPE_EMAIl
import org.kjh.mytravel.ui.features.profile.InputValidator.INPUT_TYPE_PW
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidateEmail
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidateNickName
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidatePw

/**
 * MyTravel
 * Class: MyBindingAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("bindHomeBannerItems", "errorCallback")
    fun RecyclerView.bindHomeBannerItems(
        uiState         : UiState<List<Banner>>,
        initStateAction : () -> Unit
    ) {
        when (uiState) {
            is UiState.Success -> (adapter as? BannerListAdapter)?.submitList(uiState.data)
            is UiState.Error   -> {
                Toast.makeText(context, uiState.errorMsg.res, Toast.LENGTH_SHORT).show()
                initStateAction()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindPlaceRankingItems", "placeRankingListAdapter", "errorCallback")
    fun RecyclerView.bindHomeRankingItems(
        placeRankingUiState    : UiState<List<PlaceWithRanking>>,
        placeRankingListAdapter: PlaceRankingListAdapter?,
        initStateAction        : () -> Unit
    ) {
        when (placeRankingUiState) {
            is UiState.Success -> placeRankingListAdapter?.submitList(placeRankingUiState.data)
            is UiState.Error   -> {
                Toast.makeText(context, placeRankingUiState.errorMsg.res, Toast.LENGTH_SHORT).show()
                initStateAction()
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bindLatestPostItems", "latestPostListAdapter", "scope"], requireAll = true)
    fun RecyclerView.bindLatestPostItems(
        pagingData   : Flow<PagingData<Post>>,
        adapter      : LatestPostPagingDataAdapter?,
        scope        : CoroutineScope
    ) {
        scope.launch {
            pagingData.collectLatest {
                adapter?.submitData(it)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindPostItems")
    fun RecyclerView.bindPostItems(items: List<Post>? = emptyList()) {
        val adapter = this.adapter

        adapter?.let {
            when (adapter) {
                is MyPostListAdapter      -> adapter.submitList(items)
                is PlaceDayLogListAdapter -> adapter.submitList(items)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindMediaStoreItems")
    fun RecyclerView.bindMediaStoreItems(items: List<MediaStoreImage>? = emptyList()) {
        val adapter = this.adapter

        adapter?.let {
            when (adapter) {
                is MediaStoreImageListAdapter -> adapter.submitList(items)
                is SelectedPhotoListAdapter   -> adapter.submitList(items)
                is WritePostImagesAdapter     -> adapter.submitList(items)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindMapSearchItems")
    fun RecyclerView.bindMapSearchItems(uiState: UiState<List<MapQueryItem>>) {
        val adapter = this.adapter

        adapter?.let {
            when (uiState) {
                is UiState.Success -> (adapter as MapSearchPlaceListAdapter).submitList(uiState.data)
                is UiState.Error   ->
                    Toast.makeText(context, uiState.errorMsg.res, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindPostItems", "bookmarks")
    fun RecyclerView.bindPostItemsWithBookmarkState(
        items    : List<Post>?,
        bookmarks: List<Bookmark>?
    ) {
        val postItems = items?.updateBookmarkStateWithPosts(bookmarks ?: emptyList())
            ?: emptyList()

        val adapter = this.adapter

        adapter?.let {
            (adapter as UserPostListAdapter).submitList(postItems)
        }
    }

    @JvmStatic
    @BindingAdapter("bindItems")
    fun RecyclerView.bindBookmarkItems(items: List<Bookmark>? = listOf()) {
        val adapter = this.adapter

        adapter?.let {
            (adapter as BookmarkListAdapter).submitList(items)
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
    @BindingAdapter("toolbar", "collapsingToolbar")
    fun bindAddOnOffsetChangedListener(
        appbarLayout     : AppBarLayout,
        toolbar          : MaterialToolbar,
        collapsingToolBar: CollapsingToolbarLayout
    ) {
        var scrollRange = -1

        appbarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                val isCollapsed    = scrollRange + verticalOffset == 0
                val titleColor     = if (isCollapsed) Color.BLACK else Color.WHITE
                val bgColor        = if (isCollapsed) Color.WHITE else Color.TRANSPARENT
                val statusBarScrim = if (isCollapsed) Color.WHITE else Color.TRANSPARENT

                toolbar.setTitleTextColor(titleColor)
                toolbar.setBackgroundColor(bgColor)
                collapsingToolBar.setStatusBarScrimColor(statusBarScrim)
            }
        )
    }

    @JvmStatic
    @BindingAdapter("isVisibleMenu")
    fun bindShowMenu(view: Toolbar, isVisible: Boolean) {
        view.menu[0].isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun bindVisible(view: ProgressBar, isVisible: Boolean) {
        view.isVisible = isVisible
    }


    @JvmStatic
    @BindingAdapter(value = ["app:bookmarkList", "app:placeName"], requireAll = true)
    fun bindToolbarMenuIconWithBookmark(
        view      : Toolbar,
        items     : List<Bookmark>?,
        placeName : String
    ) {
        val isBookmarked = items?.isBookmarkedPlace(placeName) ?: false

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
    fun TextInputEditText.onFocusLost(v: TextInputLayout) {
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                v.error          = null
                v.isErrorEnabled = false
            } else {
                val inputText = this.text.toString()
                v.error = when (this.tag) {
                    INPUT_TYPE_EMAIl -> isValidateEmail(inputText).errorMsg
                    INPUT_TYPE_PW    -> isValidatePw(inputText).errorMsg
                    else -> isValidateNickName(inputText).errorMsg
                }
                v.isErrorEnabled = when (this.tag) {
                    INPUT_TYPE_EMAIl -> isValidateEmail(inputText) != InputValidator.EMAIL.VALIDATE
                    INPUT_TYPE_PW    -> isValidatePw(inputText) != InputValidator.PW.VALIDATE
                    else -> isValidateNickName(inputText) != InputValidator.NICKNAME.VALIDATE
                }
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