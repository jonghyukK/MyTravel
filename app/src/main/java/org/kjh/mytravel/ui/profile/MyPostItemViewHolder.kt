package org.kjh.mytravel.ui.profile

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: MyPostItemViewHolder
 * Created by jonghyukkang on 2022/04/27.
 *
 * Description:
 */
class MyPostItemViewHolder(
    val binding: VhProfilePostItemBinding,
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item

        itemView.setOnClickListener {
            onClickPost(item)
        }

        binding.ivBookmark.setOnClickListener {
            onClickBookmark(item)
        }
    }
}