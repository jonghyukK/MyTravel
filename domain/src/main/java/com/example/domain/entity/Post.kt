package com.example.domain.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * MyTravel
 * Class: Post
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class Post(
    val postId: Int,
    val email: String,
    val nickName: String,
    val content: String? = null,
    val cityName: String,
    val subCityName: String,
    val placeName: String,
    val placeAddress: String,
    val profileImg: String? = null,
    val createdDate: String,
    val imageUrl: List<String> = listOf()
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

