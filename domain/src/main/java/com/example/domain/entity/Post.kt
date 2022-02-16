package com.example.domain.entity

import androidx.recyclerview.widget.DiffUtil

/**
 * MyTravel
 * Class: Post
 * Created by jonghyukkang on 2022/01/28.
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
) {
    companion object {
        val DiffCallback = object: DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.postId == newItem.postId

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }
}
