package org.kjh.mytravel.ui.features.user

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: UserPostItemViewHolder
 * Created by jonghyukkang on 2022/04/27.
 *
 * Description:
 */
class UserPostItemViewHolder(
    val binding: VhProfilePostItemBinding,
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item

        itemView.onThrottleClick {
            onClickPost(item)
        }

        binding.ivBookmark.onThrottleClick {
            onClickBookmark(item)
        }
    }
}
