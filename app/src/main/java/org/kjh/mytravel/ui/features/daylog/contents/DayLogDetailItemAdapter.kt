package org.kjh.mytravel.ui.features.daylog.contents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhDayLogDetailItemBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: ImageAdapter
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */

class DayLogDetailItemAdapter(
    private val onClickBookmark: (DayLog) -> Unit,
    private val onClickShare   : (DayLog) -> Unit
): RecyclerView.Adapter<DayLogDetailItemAdapter.DayLogDetailItemViewHolder>() {

    private var currentDayLogItem: DayLog? = null
    private var isBookmarked   : Boolean = false

    fun setDayLogItem(dayLog: DayLog) {
        currentDayLogItem = dayLog
        notifyItemChanged(0)
    }

    fun setBookmarked(value: Boolean) {
        isBookmarked = value
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayLogDetailItemViewHolder {
        return DayLogDetailItemViewHolder(
            VhDayLogDetailItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickBookmark, onClickShare
        )
    }

    override fun onBindViewHolder(holder: DayLogDetailItemViewHolder, position: Int) {
        currentDayLogItem?.let { holder.bind(it, isBookmarked)}
    }

    override fun getItemCount() = 1

    class DayLogDetailItemViewHolder(
        private val binding        : VhDayLogDetailItemBinding,
        private val onClickBookmark: (DayLog) -> Unit,
        private val onClickShare   : (DayLog) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clPlaceInfoContainer.onThrottleClick { view ->
                binding.dayLogItem?.let { dayLog ->
                    view.navigateTo(
                        NavGraphDirections.actionGlobalPlaceInfoWithDayLogFragment(dayLog.placeName))
                }
            }

            binding.btnBookmark.onThrottleClick {
                binding.dayLogItem?.let { dayLog ->
                    onClickBookmark(dayLog)
                }
            }

            binding.btnShare.onThrottleClick {
                binding.dayLogItem?.let { dayLog ->
                    onClickShare(dayLog)
                }
            }
        }

        fun bind(dayLogItem: DayLog, isBookmarked: Boolean) {
            binding.dayLogItem = dayLogItem
            binding.isBookmarked = isBookmarked
        }
    }
}
