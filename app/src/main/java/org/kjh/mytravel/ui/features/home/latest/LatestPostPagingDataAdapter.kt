package org.kjh.mytravel.ui.features.home.latest

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRecentItemBinding
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: PlaceListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class LatestPostPagingDataAdapter(
    private val onClickPost: (Post) -> Unit
): PagingDataAdapter<Post, LatestPostPagingDataAdapter.LatestPostViewHolder>(Post.diffCallback) {
    private val state = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LatestPostViewHolder(
            VhPlaceRecentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: LatestPostViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onViewRecycled(holder: LatestPostViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        state[key] = holder.binding.rvRecentPlaceList.layoutManager?.onSaveInstanceState()
    }

    inner class LatestPostViewHolder(
        val binding: VhPlaceRecentItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        lateinit var pageSnapHelper: PagerSnapHelper

        fun bind(item: Post) {
            binding.post = item

            itemView.setOnClickListener {
                onClickPost(item)
            }

            binding.rvRecentPlaceList.apply {
                adapter = LatestPostImageAdapter(item.imageUrl) {
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