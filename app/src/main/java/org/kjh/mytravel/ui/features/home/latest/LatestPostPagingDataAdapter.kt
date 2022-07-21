package org.kjh.mytravel.ui.features.home.latest

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhLinearPostRowItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.ui.features.profile.LineIndicatorDecoration
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
            VhLinearPostRowItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: LatestPostViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class LatestPostViewHolder(
        val binding: VhLinearPostRowItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val snapHelper = PagerSnapHelper()
        private val imagesAdapter = LatestPostImageAdapter {
            binding.postItem?.let { post ->
                binding.root.navigateToDayLogDetail(post.placeName)
            }
        }

        init {
            itemView.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateToDayLogDetail(post.placeName, post.postId)
                }
            }

            binding.postImgRecyclerView.apply {
                setHasFixedSize(true)
                adapter = imagesAdapter
                addItemDecoration(LineIndicatorDecoration())
                snapHelper.attachToRecyclerView(this)
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object : OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.postImgRecyclerView.layoutManager?.onSaveInstanceState()
                            }
                        }
                    ))
            }
        }

        fun bind(item: Post) {
            binding.postItem = item

            imagesAdapter.setItems(item.imageUrl)
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.postImgRecyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.postImgRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }
}