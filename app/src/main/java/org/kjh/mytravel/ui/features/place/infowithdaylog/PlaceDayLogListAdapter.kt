package org.kjh.mytravel.ui.features.place.infowithdaylog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceDayLogItemBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceDayLogListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */

class PlaceDayLogListAdapter
    : ListAdapter<DayLog, PlaceDayLogListAdapter.PlaceDayLogViewHolder>(DayLog.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDayLogViewHolder(
            VhPlaceDayLogItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceDayLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlaceDayLogViewHolder(
        val binding: VhPlaceDayLogItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.dayLogItem?.let { dayLog ->
                    view.navigateToDayLogDetail(dayLog.placeName, dayLog.dayLogId)
                }
            }
        }

        fun bind(dayLogItem: DayLog) {
            binding.dayLogItem = dayLogItem
        }
    }
}