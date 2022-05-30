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
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.ui.features.profile.my.ProfilePostsFragment.Companion.TYPE_GRID
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: MyPostListAdapter
 * Created by jonghyukkang on 2022/04/27.
 *
 * Description:
 */
class PostMultipleViewAdapter(
    private val viewType       : Int = TYPE_GRID,
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, RecyclerView.ViewHolder>(Post.diffCallback) {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (this.viewType) {
            TYPE_GRID ->
                PostGridItemViewHolder(
                    VhGridPostItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onClickPost, onClickBookmark
                )

            else ->
                PostsLinearOuterViewHolder(
                    VhLinearPostRowItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewType) {
            TYPE_GRID -> (holder as PostGridItemViewHolder).bind(getItem(position))
            else -> (holder as PostsLinearOuterViewHolder).bind(getItem(position))
        }
    }

    // Linear ViewHolder for Posts.
    inner class PostsLinearOuterViewHolder(
        val binding: VhLinearPostRowItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper   : PagerSnapHelper = PagerSnapHelper()
        private val imageAdapters: PostsLinearImageAdapter = PostsLinearImageAdapter {
            onClickPost(getItem(bindingAdapterPosition))
        }

        init {
            binding.postImgRecyclerView.apply {
                itemAnimator = null
                setHasFixedSize(true)
                adapter = imageAdapters
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

            binding.ivBookmark.onThrottleClick {
                onClickBookmark(item)
            }

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
        private val onClickPost    : (Post) -> Unit,
        private val onClickBookmark: (Post) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.postItem = item

            itemView.onThrottleClick {
                onClickPost(item)
            }

            binding.ivBookmark.onThrottleClick {
                onClickBookmark(item)
            }
        }
    }
}
