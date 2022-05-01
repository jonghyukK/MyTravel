package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.PostEntity

/**
 * MyTravel
 * Class: Post
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class Post(
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
    val isBookmarked: Boolean = false,
    val imageUrl    : List<String> = listOf()
){
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(
                oldItem: Post,
                newItem: Post
            ): Boolean =
                oldItem.postId == newItem.postId

            override fun areContentsTheSame(
                oldItem: Post,
                newItem: Post
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun PostEntity.mapToPresenter() =
    Post(
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

fun List<Post>.updateBookmarkStateWithPosts(bookmarks: List<Bookmark>) =
    this.map { post ->
        post.copy(
            isBookmarked = bookmarks.find { it.placeName == post.placeName } != null
        )
    }
