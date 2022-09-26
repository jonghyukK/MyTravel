package org.kjh.mytravel.ui.features.home.latest

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.LayoutHomeSectionHeaderBinding
import org.kjh.mytravel.databinding.VhLatestPostRowItemBinding
import org.kjh.mytravel.model.LatestPostItemUiState
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.ui.features.home.LatestPostUiState
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
class LatestPostPagingDataAdapter(
    private val onClickMenu : (LatestPostItemUiState) -> Unit
) : PagingDataAdapter<LatestPostUiState, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun getItemViewType(position: Int) = when (peek(position)) {
        is LatestPostUiState.HeaderItem -> R.layout.layout_home_section_header
        is LatestPostUiState.PagingItem -> R.layout.vh_latest_post_row_item
        else -> throw IllegalStateException("Unknown view")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        R.layout.layout_home_section_header ->
            HeaderViewHolder(
                LayoutHomeSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        else -> LatestPostViewHolder(
                VhLatestPostRowItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (item is LatestPostUiState.HeaderItem) {
            (holder as HeaderViewHolder).bind("최근 올라온 여행지")
        } else if (item is LatestPostUiState.PagingItem) {
            (holder as LatestPostViewHolder).bind(item.item)
        }
    }

    class HeaderViewHolder(
        val binding: LayoutHomeSectionHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.headerTitle = title
        }
    }


    inner class LatestPostViewHolder(
        val binding: VhLatestPostRowItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val snapHelper = PagerSnapHelper()
        private val imagesAdapter = LatestPostImageAdapter {
            binding.latestPostItem?.let { item ->
                binding.root.navigateToDayLogDetail(item.placeName, item.postId)
            }
        }

        init {
            itemView.onThrottleClick { view ->
                binding.latestPostItem?.let { item ->
                    view.navigateToDayLogDetail(item.placeName, item.postId)
                }
            }

            binding.tvNickName.onThrottleClick { view ->
                binding.latestPostItem?.let { item ->
                    view.navigateTo(NavGraphDirections.actionGlobalUserFragment(item.email))
                }
            }

            binding.sivProfileImg.onThrottleClick { view ->
                binding.latestPostItem?.let { item ->
                    view.navigateTo(NavGraphDirections.actionGlobalUserFragment(item.email))
                }
            }

            binding.ivMore.onThrottleClick {
                binding.latestPostItem?.let {
                    onClickMenu(it)
                }
            }

            binding.ivBookmark.onThrottleClick {
                binding.latestPostItem?.onBookmark?.invoke()
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

        fun bind(item: LatestPostItemUiState) {
            binding.latestPostItem = item

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
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<LatestPostUiState>() {
            override fun areItemsTheSame(
                oldItem: LatestPostUiState,
                newItem: LatestPostUiState
            ): Boolean {
                val isSameHeaderItem = oldItem is LatestPostUiState.HeaderItem
                        && newItem is LatestPostUiState.HeaderItem

                val isSamePagingItem = oldItem is LatestPostUiState.PagingItem
                        && newItem is LatestPostUiState.PagingItem
                        && oldItem.item.postId == newItem.item.postId

                return isSameHeaderItem || isSamePagingItem
            }

            override fun areContentsTheSame(
                oldItem: LatestPostUiState,
                newItem: LatestPostUiState
            ) = oldItem == newItem
        }
    }
}

