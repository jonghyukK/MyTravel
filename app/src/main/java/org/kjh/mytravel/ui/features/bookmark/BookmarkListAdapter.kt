package org.kjh.mytravel.ui.features.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBookmarkPostItemBinding
import org.kjh.mytravel.model.Bookmark

/**
 * MyTravel
 * Class: BookmarkListAdapter
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
class BookmarkListAdapter(
    private val onClickItem: (Bookmark) -> Unit,
    private val onClickBookmark: (Bookmark) -> Unit
): ListAdapter<Bookmark, BookmarkItemSmallViewHolder>(Bookmark.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookmarkItemSmallViewHolder(
            VhBookmarkPostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem, onClickBookmark
        )

    override fun onBindViewHolder(holder: BookmarkItemSmallViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class BookmarkItemSmallViewHolder(
    val binding: VhBookmarkPostItemBinding,
    private val onClickItem: (Bookmark) -> Unit,
    private val onClickBookmark: (Bookmark) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Bookmark) {
        binding.bookmarkItem = item

        itemView.setOnClickListener {
            onClickItem(item)
        }

        binding.ivBookmark.setOnClickListener {
            onClickBookmark(item)
        }
    }
}
