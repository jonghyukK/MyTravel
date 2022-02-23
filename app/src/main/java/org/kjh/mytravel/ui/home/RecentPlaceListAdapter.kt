package org.kjh.mytravel.ui.home

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Post
import org.kjh.mytravel.databinding.VhPlaceRecentItemBinding
import org.kjh.mytravel.ui.RectImageListAdapter

/**
 * MyTravel
 * Class: PlaceListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class RecentPlaceListAdapter(
    private val onClickPost: (Post) -> Unit
): PagingDataAdapter<Post, RecentPlaceListAdapter.PlaceItemViewHolder>(Post.DiffCallback) {
    private val state = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceItemViewHolder(
            VhPlaceRecentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onViewRecycled(holder: PlaceItemViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        state[key] = holder.binding.rvRecentPlaceList.layoutManager?.onSaveInstanceState()
    }

    inner class PlaceItemViewHolder(
        val binding: VhPlaceRecentItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        lateinit var pageSnapHelper: PagerSnapHelper

        fun bind(item: Post) {
            binding.post = item

            itemView.setOnClickListener {
                onClickPost(item)
            }

            binding.rvRecentPlaceList.apply {
                adapter = RectImageListAdapter(item.imageUrl) {
                    onClickPost(item)
                }
                setHasFixedSize(true)
                pageSnapHelper = PagerSnapHelper()

                if (onFlingListener == null) {
                    pageSnapHelper.attachToRecyclerView(this)
                }
            }

            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = state[key]

            if (state != null) {
                binding.rvRecentPlaceList.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvRecentPlaceList.layoutManager?.scrollToPosition(0)
            }
        }
    }
}