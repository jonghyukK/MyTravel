package org.kjh.mytravel.ui.features.profile

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhGridPostItemBinding
import org.kjh.mytravel.databinding.VhLinearPostRowItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: MyPostListAdapter
 * Created by jonghyukkang on 2022/04/27.
 *
 * Description:
 */

sealed class ViewType {
    object Grid  : ViewType()
    object Linear: ViewType()
}

class PostMultipleViewAdapter(
    private val viewType       : ViewType = ViewType.Grid,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, RecyclerView.ViewHolder>(Post.diffCallback) {

    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (this.viewType) {
            ViewType.Grid ->
                PostGridItemViewHolder(
                    VhGridPostItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onClickBookmark
                )
            ViewType.Linear ->
                PostsLinearOuterViewHolder(
                    VhLinearPostRowItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewType) {
            ViewType.Grid   -> (holder as PostGridItemViewHolder).bind(getItem(position))
            ViewType.Linear -> (holder as PostsLinearOuterViewHolder).bind(getItem(position))
        }
    }

    // Linear ViewHolder for Posts.
    inner class PostsLinearOuterViewHolder(
        val binding: VhLinearPostRowItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val snapHelper =  PagerSnapHelper()
        private val imageAdapters = PostsLinearImageAdapter {
            binding.postItem?.let { post ->
                binding.root.navigateToDayLogDetail(post.placeName, post.postId)
            }
        }

        init {
            binding.postImgRecyclerView.apply {
                itemAnimator = null
                setHasFixedSize(true)
                adapter = imageAdapters
                addItemDecoration(LineIndicatorDecoration())
                addOnItemTouchListener(OnNestedHorizontalTouchListener())
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

            binding.ivBookmark.onThrottleClick {
                binding.postItem?.let {
                    onClickBookmark(it)
                }
            }
        }

        fun bind(item: Post) {
            binding.postItem = item

            imageAdapters.setItems(item.imageUrl)
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

    // Grid ViewHolder for Posts.
    class PostGridItemViewHolder(
        private val binding        : VhGridPostItemBinding,
        private val onClickBookmark: (Post) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateToDayLogDetail(post.placeName, post.postId)
                }
            }

            binding.ivBookmark.onThrottleClick {
                binding.postItem?.let {
                    onClickBookmark(it)
                }
            }
        }

        fun bind(item: Post) {
            binding.postItem = item
        }
    }
}
