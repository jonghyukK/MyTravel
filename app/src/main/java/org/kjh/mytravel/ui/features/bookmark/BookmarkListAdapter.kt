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
    private val onClickPost    : (String) -> Unit,
    private val onClickBookmark: (Bookmark) -> Unit
): ListAdapter<Bookmark, BookmarkItemViewHolder>(Bookmark.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookmarkItemViewHolder(
            VhBookmarkPostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPost, onClickBookmark
        )

    override fun onBindViewHolder(holder: BookmarkItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}