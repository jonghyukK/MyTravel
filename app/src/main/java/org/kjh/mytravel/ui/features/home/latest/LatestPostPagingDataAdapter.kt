package org.kjh.mytravel.ui.features.home.latest

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.LayoutHomeSectionHeaderBinding
import org.kjh.mytravel.databinding.VhAroundPlaceHeaderItemBinding
import org.kjh.mytravel.databinding.VhAroundPlaceItemBinding
import org.kjh.mytravel.databinding.VhLinearPostRowItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.ui.features.daylog.AroundPlaceUiModel
import org.kjh.mytravel.ui.features.home.LatestPostUiModel
import org.kjh.mytravel.ui.features.profile.LineIndicatorDecoration
import org.kjh.mytravel.utils.navigateTo
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
    : PagingDataAdapter<LatestPostUiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is LatestPostUiModel.HeaderItem -> TYPE_HEADER_ITEM
            is LatestPostUiModel.PostItem   -> TYPE_POST_ITEM
            null -> throw IllegalArgumentException("Not Found ViewHolder Type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            TYPE_HEADER_ITEM -> LatestPostsHeaderItemViewHolder(
                LayoutHomeSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            TYPE_POST_ITEM -> LatestPostViewHolder(
                VhLinearPostRowItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Not Found ViewHolder Type $viewType")
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { uiModel ->
            when (uiModel) {
                is LatestPostUiModel.PostItem -> (holder as LatestPostViewHolder).bind(uiModel.post)
                is LatestPostUiModel.HeaderItem -> (holder as LatestPostsHeaderItemViewHolder).bind(uiModel.headerTitle)
            }
        }
    }

    class LatestPostsHeaderItemViewHolder(
        private val binding: LayoutHomeSectionHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(headerText: String) {
            binding.headerTitle = headerText
        }
    }

    inner class LatestPostViewHolder(
        val binding: VhLinearPostRowItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val snapHelper = PagerSnapHelper()
        private val imagesAdapter = LatestPostImageAdapter {
            binding.postItem?.let { post ->
                binding.root.navigateToDayLogDetail(post.placeName, post.postId)
            }
        }

        init {
            itemView.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateToDayLogDetail(post.placeName, post.postId)
                }
            }

            binding.tvNickName.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateTo(NavGraphDirections.actionGlobalUserFragment(post.email))
                }
            }

            binding.sivProfileImg.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateTo(NavGraphDirections.actionGlobalUserFragment(post.email))
                }
            }

            binding.postImgRecyclerView.apply {
                setHasFixedSize(true)
                adapter = imagesAdapter
                addItemDecoration(LineIndicatorDecoration())
                snapHelper.attachToRecyclerView(this)
                addOnItemTouchListener(OnNestedHorizontalTouchListener())
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

    companion object {
        const val TYPE_HEADER_ITEM = 1
        const val TYPE_POST_ITEM = 2

        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<LatestPostUiModel>() {
            override fun areItemsTheSame(oldItem: LatestPostUiModel, newItem: LatestPostUiModel) =
                (oldItem is LatestPostUiModel.PostItem && newItem is LatestPostUiModel.PostItem &&
                        oldItem.post.postId == oldItem.post.postId) ||
                        (oldItem is LatestPostUiModel.HeaderItem && newItem is LatestPostUiModel.HeaderItem &&
                                oldItem.headerTitle == newItem.headerTitle)

            override fun areContentsTheSame(oldItem: LatestPostUiModel, newItem: LatestPostUiModel) =
                oldItem == newItem
        }
    }
}

