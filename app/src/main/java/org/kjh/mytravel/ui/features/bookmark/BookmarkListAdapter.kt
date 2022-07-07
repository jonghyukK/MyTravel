package org.kjh.mytravel.ui.features.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBookmarkPostItemBinding
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: BookmarkListAdapter
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
class BookmarkListAdapter(
    private val onClickBookmark: (Bookmark) -> Unit
): ListAdapter<Bookmark, BookmarkListAdapter.BookmarkItemViewHolder>(Bookmark.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookmarkItemViewHolder(
            VhBookmarkPostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickBookmark
        )

    override fun onBindViewHolder(holder: BookmarkItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookmarkItemViewHolder(
        private val binding        : VhBookmarkPostItemBinding,
        private val onClickBookmark: (Bookmark) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.bookmarkItem?.let { bookmark ->
                    view.navigateToDayLogDetail(bookmark.placeName)
                }
            }

            binding.ivBookmark.onThrottleClick { view ->
                binding.bookmarkItem?.let { bookmark ->
                    onClickBookmark(bookmark)
                }
            }
        }

        fun bind(item: Bookmark) {
            binding.bookmarkItem = item
        }
    }
}