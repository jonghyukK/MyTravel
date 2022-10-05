package org.kjh.mytravel.ui.features.place.subcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceByCityNameItemBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlacesBySubCityDayLogListAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityDayLogListAdapter
    : RecyclerView.Adapter<PlacesBySubCityDayLogListAdapter.PlacesBySubCityDayLogViewHolder>() {

    private val dayLogItems = mutableListOf<DayLog>()

    fun setDayLogItems(items: List<DayLog>) {
        dayLogItems.clear()
        dayLogItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlacesBySubCityDayLogViewHolder(
            VhPlaceByCityNameItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlacesBySubCityDayLogViewHolder, position: Int) {
        holder.bind(dayLogItems[position])
    }

    override fun getItemCount() = dayLogItems.size

    class PlacesBySubCityDayLogViewHolder(
        private val binding : VhPlaceByCityNameItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.dayLogItem?.let { dayLog ->
                    view.navigateToDayLogDetail(dayLog.placeName, dayLog.dayLogId)
                }
            }
        }

        fun bind(item: DayLog) {
            binding.dayLogItem = item
        }
    }
}