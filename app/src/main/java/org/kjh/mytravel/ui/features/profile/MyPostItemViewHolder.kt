package org.kjh.mytravel.ui.features.profile

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: MyPostItemViewHolder
 * Created by jonghyukkang on 2022/04/27.
 *
 * Description:
 */
class MyPostItemViewHolder(
    val binding: VhProfilePostItemBinding,
    private val onClickPost    : (String) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item

        itemView.onThrottleClick {
            onClickPost(item.placeName)
        }

        binding.ivBookmark.onThrottleClick {
            onClickBookmark(item)
        }
    }
}