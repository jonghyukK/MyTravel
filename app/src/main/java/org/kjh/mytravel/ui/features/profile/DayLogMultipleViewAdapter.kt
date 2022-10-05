package org.kjh.mytravel.ui.features.profile

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhGridDayLogItemBinding
import org.kjh.mytravel.databinding.VhLinearDayLogRowItemBinding
import org.kjh.mytravel.model.DayLog
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

class DayLogMultipleViewAdapter(
    private val viewType       : ViewType = ViewType.Grid,
    private val onClickBookmark: (DayLog) -> Unit,
    private val onClickMore    : (DayLog) -> Unit
): ListAdapter<DayLog, RecyclerView.ViewHolder>(DayLog.diffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).dayLogId.toLong()
    }

    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (this.viewType) {
            ViewType.Grid ->
                DayLogGridItemViewHolder(
                    VhGridDayLogItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onClickBookmark
                )
            ViewType.Linear ->
                DayLogsLinearOuterViewHolder(
                    VhLinearDayLogRowItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewType) {
            ViewType.Grid   -> (holder as DayLogGridItemViewHolder).bind(getItem(position))
            ViewType.Linear -> (holder as DayLogsLinearOuterViewHolder).bind(getItem(position))
        }
    }

    // Linear ViewHolder for Posts.
    inner class DayLogsLinearOuterViewHolder(
        val binding: VhLinearDayLogRowItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val snapHelper =  PagerSnapHelper()
        private val imageAdapters = DayLogsLinearImageAdapter {
            binding.dayLogItem?.let { dayLog ->
                binding.root.navigateToDayLogDetail(dayLog.placeName, dayLog.dayLogId)
            }
        }

        init {
            binding.dayLogImgRecyclerView.apply {
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
                                    binding.dayLogImgRecyclerView.layoutManager?.onSaveInstanceState()
                            }
                        }
                    ))
            }

            binding.ivBookmark.onThrottleClick {
                binding.dayLogItem?.let {
                    onClickBookmark(it)
                }
            }

            binding.ivMore.onThrottleClick {
                binding.dayLogItem?.let {
                    onClickMore(it)
                }
            }
        }

        fun bind(item: DayLog) {
            binding.dayLogItem = item

            imageAdapters.setItems(item.imageUrl)
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

    // Grid ViewHolder for Posts.
    class DayLogGridItemViewHolder(
        private val binding        : VhGridDayLogItemBinding,
        private val onClickBookmark: (DayLog) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.dayLogItem?.let { dayLog ->
                    view.navigateToDayLogDetail(dayLog.placeName, dayLog.dayLogId)
                }
            }

            binding.ivBookmark.onThrottleClick {
                binding.dayLogItem?.let {
                    onClickBookmark(it)
                }
            }
        }

        fun bind(item: DayLog) {
            binding.dayLogItem = item
        }
    }
}
