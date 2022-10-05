package org.kjh.mytravel.ui.features.daylog

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhDayLogDetailBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.ui.features.daylog.images.DayLogDetailImagesInnerAdapter
import org.kjh.mytravel.ui.features.daylog.profiles.DayLogDetailUserProfileItemDecoration
import org.kjh.mytravel.ui.features.daylog.profiles.DayLogDetailUserProfilesInnerAdapter
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: DayLogDetailAdapter
 * Created by jonghyukkang on 2022/09/27.
 *
 * Description:
 */
class DayLogDetailAdapter(
    private val onClickShare: (DayLog) -> Unit
): RecyclerView.Adapter<DayLogDetailAdapter.DayLogDetailViewHolder>() {
    private val imageListAdapter = DayLogDetailImagesInnerAdapter()
    private val profileListAdapter = DayLogDetailUserProfilesInnerAdapter()
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    private var uiItem: DayLogDetailUiState? = null

    fun setUiItem(item: DayLogDetailUiState) {
        if (uiItem == null || uiItem != item) {
            uiItem = item
            notifyItemChanged(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayLogDetailViewHolder(
            VhDayLogDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: DayLogDetailViewHolder, position: Int) {
        uiItem?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = 1

    inner class DayLogDetailViewHolder(
        private val binding: VhDayLogDetailBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()

        init {
            binding.imageRecyclerView.apply {
                setHasFixedSize(true)
                adapter = imageListAdapter
                addItemDecoration(FullLineIndicatorDecoration())
                snapHelper.attachToRecyclerView(this)
                addOnItemTouchListener(OnNestedHorizontalTouchListener())
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object: OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.imageRecyclerView.layoutManager?.onSaveInstanceState()
                            }
                        }
                    )
                )
            }

            binding.userProfileRecyclerView.apply {
                setHasFixedSize(true)
                adapter = profileListAdapter
                addItemDecoration(DayLogDetailUserProfileItemDecoration())
            }

            binding.clPlaceInfoContainer.onThrottleClick { view ->
                binding.dayLogItem?.currentDayLogItem?.let { dayLog ->
                    view.navigateTo(
                        NavGraphDirections.actionGlobalPlaceInfoWithDayLogFragment(dayLog.placeName)
                    )
                }
            }

            binding.btnShare.onThrottleClick {
                binding.dayLogItem?.currentDayLogItem?.let { item ->
                    onClickShare(item)
                }
            }
        }

        fun bind(item: DayLogDetailUiState) {
            binding.dayLogItem = item
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.imageRecyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.imageRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }
}

