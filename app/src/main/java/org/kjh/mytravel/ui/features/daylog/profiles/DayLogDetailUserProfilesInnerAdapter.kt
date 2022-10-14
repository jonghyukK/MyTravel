package org.kjh.mytravel.ui.features.daylog.profiles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhDayLogUserProfileItemBinding
import org.kjh.mytravel.ui.common.setOnThrottleClickListener
import org.kjh.mytravel.ui.features.daylog.DayLogProfileItemUiState
import org.kjh.mytravel.utils.navigateTo

/**
 * MyTravel
 * Class: PlaceDetailUserProfileListAdapter
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */

class DayLogDetailUserProfilesInnerAdapter
    : ListAdapter<DayLogProfileItemUiState, PlaceDetailUserProfileViewHolder>(SELECTABLE_DAY_LOG_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceDetailUserProfileViewHolder {
        return PlaceDetailUserProfileViewHolder(
            VhDayLogUserProfileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceDetailUserProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val SELECTABLE_DAY_LOG_COMPARATOR =
            object : DiffUtil.ItemCallback<DayLogProfileItemUiState>() {
                override fun areItemsTheSame(
                    oldItem: DayLogProfileItemUiState,
                    newItem: DayLogProfileItemUiState
                ) = (oldItem.nickName == newItem.nickName) ||
                        (oldItem.isSelected == newItem.isSelected)

                override fun areContentsTheSame(
                    oldItem: DayLogProfileItemUiState,
                    newItem: DayLogProfileItemUiState
                ) = oldItem == newItem
            }
    }
}

class PlaceDetailUserProfileViewHolder(
    val binding: VhDayLogUserProfileItemBinding
): RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnThrottleClickListener {
            binding.dayLogItem?.let { dayLog ->
                dayLog.onChangeSelected()
            }
        }

        binding.tvNickName.setOnThrottleClickListener { view ->
            binding.dayLogItem?.let { dayLog ->
                val action = NavGraphDirections.actionGlobalUserFragment(dayLog.email)
                view.navigateTo(action)
            }
        }
    }

    fun bind(dayLogItem: DayLogProfileItemUiState) {
        binding.dayLogItem = dayLogItem
    }
}