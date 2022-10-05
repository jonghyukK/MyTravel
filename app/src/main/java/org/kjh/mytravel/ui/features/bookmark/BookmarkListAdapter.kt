package org.kjh.mytravel.ui.features.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBookmarkDayLogItemBinding
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: BookmarkListAdapter
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
class BookmarkListAdapter
    : ListAdapter<BookmarkItemUiState, BookmarkItemViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookmarkItemViewHolder(
            VhBookmarkDayLogItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: BookmarkItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val UIMODEL_COMPARATOR =
            object : DiffUtil.ItemCallback<BookmarkItemUiState>() {
                override fun areItemsTheSame(
                    oldItem: BookmarkItemUiState,
                    newItem: BookmarkItemUiState
                ) = oldItem.placeName == newItem.placeName

                override fun areContentsTheSame(
                    oldItem: BookmarkItemUiState,
                    newItem: BookmarkItemUiState
                ) = oldItem == newItem
            }
    }
}


class BookmarkItemViewHolder(
    private val binding : VhBookmarkDayLogItemBinding
): RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.onThrottleClick { view ->
            binding.bookmarkItem?.let { item ->
                view.navigateToDayLogDetail(item.placeName)
            }
        }
    }

    fun bind(bookmarkItem: BookmarkItemUiState) {
        binding.bookmarkItem = bookmarkItem
    }
}