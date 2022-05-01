package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.BookmarkEntity

/**
 * MyTravel
 * Class: Bookmark
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class Bookmark(
    val postId      : Int,
    val email       : String,
    val nickName    : String,
    val content     : String? = null,
    val cityName    : String,
    val subCityName : String,
    val placeName   : String,
    val placeAddress: String,
    val profileImg  : String? = null,
    val createdDate : String,
    val isBookmarked: Boolean = true,
    val imageUrl    : List<String> = listOf()
){
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Bookmark>() {
            override fun areItemsTheSame(
                oldItem: Bookmark,
                newItem: Bookmark
            ): Boolean =
                oldItem.postId == newItem.postId

            override fun areContentsTheSame(
                oldItem: Bookmark,
                newItem: Bookmark
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun BookmarkEntity.mapToPresenter() =
    Bookmark(
        postId,
        email,
        nickName,
        content,
        cityName,
        subCityName,
        placeName,
        placeAddress,
        profileImg,
        createdDate,
        isBookmarked,
        imageUrl
    )

fun List<Bookmark>.isBookmarkedPlace(placeName: String) =
    this.let { bookmarkList ->
        bookmarkList.find { it.placeName == placeName } != null
    }
