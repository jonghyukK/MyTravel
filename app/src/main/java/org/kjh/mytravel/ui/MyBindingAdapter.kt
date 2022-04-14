package org.kjh.mytravel.ui

import android.graphics.Color
import android.net.Uri
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.kjh.mytravel.InputValidator
import org.kjh.mytravel.InputValidator.INPUT_TYPE_EMAIl
import org.kjh.mytravel.InputValidator.INPUT_TYPE_PW
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidateNickName
import org.kjh.mytravel.InputValidator.isValidatePw
import org.kjh.mytravel.R
import org.kjh.mytravel.isBookmarkedPlace
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.base.UiState
import org.kjh.mytravel.ui.bookmark.BookmarkListAdapter
import org.kjh.mytravel.ui.place.PlaceDayLogListAdapter
import org.kjh.mytravel.ui.profile.edit.ProfileUpdateState
import org.kjh.mytravel.ui.profile.upload.*
import org.kjh.mytravel.updateBookmarkStateWithPosts

/**
 * MyTravel
 * Class: MyBindingAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */

object MyBindingAdapter {

    @JvmStatic
    @BindingAdapter("bindItems")
    fun RecyclerView.bindPostItems(items: List<Post>? = listOf()) {
        val adapter = this.adapter

        if (adapter != null && adapter is PostSmallListAdapter) {
            adapter.submitList(items)
        } else if (adapter is PlaceDayLogListAdapter) {
            adapter.submitList(items)
        }
    }

    @JvmStatic
    @BindingAdapter("bindItems", "bookmarks")
    fun RecyclerView.bindPostItems(items: List<Post>?, bookmarks: List<Bookmark>?) {
        val postItems = items?.updateBookmarkStateWithPosts(
            bookmarks ?: emptyList()) ?: emptyList()

        val adapter = this.adapter

        if (adapter != null && adapter is PostSmallListAdapter) {
            adapter.submitList(postItems)
        }
    }

    @JvmStatic
    @BindingAdapter("bindItems")
    fun RecyclerView.bindBookmarkItems(items: List<Bookmark>? = listOf()) {
        val adapter = this.adapter

        if (adapter != null && adapter is BookmarkListAdapter) {
            adapter.submitList(items)
        }
    }

    @JvmStatic
    @BindingAdapter("bindItems")
    fun RecyclerView.bindMediaStoreItems(items: List<MediaStoreImage>? = listOf()) {
        val adapter = this.adapter

        if (adapter != null) {
            if (adapter is MediaStoreImageListAdapter)
                adapter.submitList(items)
            else if (adapter is SelectedPhotoListAdapter)
                adapter.submitList(items)
            else if (adapter is WritePostImagesAdapter)
                adapter.submitList(items)
        }
    }

    @JvmStatic
    @BindingAdapter("bindItems")
    fun RecyclerView.bindMapSearchItems(uiState: MapSearchState) {
        when (uiState) {
            is MapSearchState.Success -> {
                val adapter = this.adapter

                if (adapter is MapSearchPlaceListAdapter)
                    adapter.submitList(uiState.items)
            }
            is MapSearchState.Error ->
                Toast.makeText(this.context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }




    @JvmStatic
    @BindingAdapter("onEditorActionListener")
    fun bindOnEditorActionListener(view: EditText, action: () -> Unit) {
        view.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                action()
            }
            false
        }
    }

    @JvmStatic
    @BindingAdapter("showLoading")
    fun bindLoading(view: ProgressBar, uiState: MapSearchState) {
        view.isVisible = uiState is MapSearchState.Loading
    }

    @JvmStatic
    @BindingAdapter("showLoading")
    fun bindLoading(view: ProgressBar, uiState: ProfileUpdateState) {
        view.isVisible = uiState is ProfileUpdateState.Loading
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
    @BindingAdapter("isVisibleMenu")
    fun bindShowMenu(view: Toolbar, isVisible: Boolean) {
        view.menu[0].isVisible = isVisible
    }








    @JvmStatic
    @BindingAdapter("imgResource")
    fun setImageDrawable(v: ImageView, imgResource: Int) {
        v.setImageResource(imgResource)
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
    fun bindErrorTextWithVisible(v: TextView, value: String?) {
        v.text = value
        v.isVisible = value != null
    }

    @JvmStatic
    @BindingAdapter("app:imgUri")
    fun bindImageWithUri(view: ImageView, imgUri: Uri?) {
        imgUri?.run {
            Glide.with(view)
                .load(imgUri)
//                .thumbnail(0.33f)
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
            .thumbnail(0.33f)
            .centerCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun bindVisible(view: ProgressBar, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter("showLoading")
    fun bindShowLoading(view: ProgressBar, uiState: UiState) {
        view.isVisible = uiState is UiState.Loading
    }
}