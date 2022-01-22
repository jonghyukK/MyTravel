package org.kjh.mytravel.data.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.mytravel.ui.uistate.PostItemUiState

/**
 * MyTravel
 * Class: Post
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */
data class Post(
    val postId: Int,
    val email: String,
    val nickName: String,
    val content: String? = null,
    val cityName: String,
    val placeName: String,
    val placeAddress: String,
    val profileImg: String? = null,
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