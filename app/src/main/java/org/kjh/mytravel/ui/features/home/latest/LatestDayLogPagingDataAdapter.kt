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
import org.kjh.mytravel.databinding.VhLatestDayLogRowItemBinding
import org.kjh.mytravel.model.LatestDayLogItemUiState
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.model.common.PagingWithHeaderUiModel
import org.kjh.mytravel.ui.common.setOnThrottleClickListener
import org.kjh.mytravel.ui.features.profile.LineIndicatorDecoration
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.navigateToDayLogDetail

/**
 * MyTravel
 * Class: PlaceListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class LatestDayLogPagingDataAdapter(
    private val onClickMenu : (LatestDayLogItemUiState) -> Unit
) : PagingDataAdapter<PagingWithHeaderUiModel<LatestDayLogItemUiState>, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun getItemViewType(position: Int) = when (peek(position)) {
        is PagingWithHeaderUiModel.HeaderItem -> VIEW_TYPE_LATEST_DAY_LOG_HEADER_ITEM
        is PagingWithHeaderUiModel.PagingItem -> VIEW_TYPE_LATEST_DAY_LOG_ITEM
        else -> throw IllegalStateException("Unknown view")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        VIEW_TYPE_LATEST_DAY_LOG_HEADER_ITEM ->
            HeaderViewHolder(
                LayoutHomeSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        VIEW_TYPE_LATEST_DAY_LOG_ITEM ->
            LatestDayLogViewHolder(
                VhLatestDayLogRowItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        else -> throw Exception("Unknown View Type")
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (item is PagingWithHeaderUiModel.HeaderItem) {
            (holder as HeaderViewHolder).bind("최근 올라온 여행지")
        } else if (item is PagingWithHeaderUiModel.PagingItem) {
            (holder as LatestDayLogViewHolder).bind(item.item)
        }
    }

    class HeaderViewHolder(
        val binding: LayoutHomeSectionHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.headerTitle = title
        }
    }

    inner class LatestDayLogViewHolder(
        val binding: VhLatestDayLogRowItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val snapHelper = PagerSnapHelper()
        private val imagesAdapter = LatestDayLogImageAdapter {
            binding.latestDayLogItem?.let { item ->
                binding.root.navigateToDayLogDetail(item.placeName, item.dayLogId)
            }
        }

        init {
            itemView.setOnThrottleClickListener { view ->
                binding.latestDayLogItem?.let { item ->
                    view.navigateToDayLogDetail(item.placeName, item.dayLogId)
                }
            }

            binding.tvNickName.setOnThrottleClickListener { view ->
                binding.latestDayLogItem?.let { item ->
                    view.navigateTo(NavGraphDirections.actionGlobalUserFragment(item.email))
                }
            }

            binding.sivProfileImg.setOnThrottleClickListener { view ->
                binding.latestDayLogItem?.let { item ->
                    view.navigateTo(NavGraphDirections.actionGlobalUserFragment(item.email))
                }
            }

            binding.ivMore.setOnThrottleClickListener {
                binding.latestDayLogItem?.let {
                    onClickMenu(it)
                }
            }

            binding.ivBookmark.setOnThrottleClickListener {
                binding.latestDayLogItem?.onBookmark?.invoke()
            }

            binding.dayLogImgRecyclerView.apply {
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
                                    binding.dayLogImgRecyclerView.layoutManager?.onSaveInstanceState()
                            }
                        }
                    ))
            }
        }

        fun bind(item: LatestDayLogItemUiState) {
            binding.latestDayLogItem = item

            imagesAdapter.setItems(item.imageUrl)
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.dayLogImgRecyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.dayLogImgRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_LATEST_DAY_LOG_ITEM = R.layout.vh_latest_day_log_row_item
        const val VIEW_TYPE_LATEST_DAY_LOG_HEADER_ITEM = R.layout.layout_home_section_header

        private val UIMODEL_COMPARATOR =
            object : DiffUtil.ItemCallback<PagingWithHeaderUiModel<LatestDayLogItemUiState>>() {
                override fun areItemsTheSame(
                    oldItem: PagingWithHeaderUiModel<LatestDayLogItemUiState>,
                    newItem: PagingWithHeaderUiModel<LatestDayLogItemUiState>
                ): Boolean {
                    val isSameHeaderItem = oldItem is PagingWithHeaderUiModel.HeaderItem
                            && newItem is PagingWithHeaderUiModel.HeaderItem

                    val isSamePagingItem = oldItem is PagingWithHeaderUiModel.PagingItem
                            && newItem is PagingWithHeaderUiModel.PagingItem
                            && oldItem.item.dayLogId == newItem.item.dayLogId

                    return isSameHeaderItem || isSamePagingItem
                }

                override fun areContentsTheSame(
                    oldItem: PagingWithHeaderUiModel<LatestDayLogItemUiState>,
                    newItem: PagingWithHeaderUiModel<LatestDayLogItemUiState>
                ) = oldItem == newItem
            }
    }
}

