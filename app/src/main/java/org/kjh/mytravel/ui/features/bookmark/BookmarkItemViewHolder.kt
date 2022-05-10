package org.kjh.mytravel.ui.features.bookmark

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBookmarkPostItemBinding
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: BookmarkItemViewHolder
 * Created by jonghyukkang on 2022/05/10.
 *
 * Description:
 */

class BookmarkItemViewHolder(
    private val binding        : VhBookmarkPostItemBinding,
    private val onClickItem    : (Bookmark) -> Unit,
    private val onClickBookmark: (Bookmark) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Bookmark) {
        binding.bookmarkItem = item

        itemView.onThrottleClick {
            onClickItem(item)
        }

        binding.ivBookmark.onThrottleClick {
            onClickBookmark(item)
        }
    }
}
