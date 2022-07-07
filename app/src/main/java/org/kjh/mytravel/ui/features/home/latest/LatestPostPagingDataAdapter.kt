package org.kjh.mytravel.ui.features.home.latest

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRecentItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class LatestPostPagingDataAdapter
    : PagingDataAdapter<Post, LatestPostPagingDataAdapter.LatestPostViewHolder>(Post.diffCallback) {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

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
        childViewState[key] = holder.binding.rvRecentPlaceList.layoutManager?.onSaveInstanceState()
    }

    inner class LatestPostViewHolder(
        val binding: VhPlaceRecentItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val snapHelper = PagerSnapHelper()
        private val imagesAdapter = LatestPostImageAdapter {
            binding.post?.let { post ->
                binding.root.navigateToDayLogDetail(post.placeName)
            }
        }

        init {
            itemView.onThrottleClick { view ->
                binding.post?.let { post ->
                    view.navigateToDayLogDetail(post.placeName, post.postId)
                }
            }

            binding.rvRecentPlaceList.apply {
                setHasFixedSize(true)
                adapter = imagesAdapter
                snapHelper.attachToRecyclerView(this)
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object : OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.rvRecentPlaceList.layoutManager?.onSaveInstanceState()
                            }
                        }
                    ))
            }
        }

        fun bind(item: Post) {
            binding.post = item

            imagesAdapter.setItems(item.imageUrl)
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.rvRecentPlaceList.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvRecentPlaceList.layoutManager?.scrollToPosition(0)
            }
        }
    }
}