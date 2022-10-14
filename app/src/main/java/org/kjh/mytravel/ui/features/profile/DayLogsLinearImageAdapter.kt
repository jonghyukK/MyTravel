package org.kjh.mytravel.ui.features.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhSquareImageLargeBinding
import org.kjh.mytravel.ui.common.setOnThrottleClickListener

/**
 * MyTravel
 * Class: DayLogsLinearImageAdapter
 * Created by jonghyukkang on 2022/05/23.
 *
 * Description:
 */

class DayLogsLinearImageAdapter(
    private val onClickDayLogImg: () -> Unit
) : RecyclerView.Adapter<DayLogsLinearImageAdapter.DayLogLinearInnerViewHolder>() {

    private val dayLogImages = mutableListOf<String>()

    fun setItems(items: List<String>) {
        dayLogImages.clear()
        dayLogImages.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayLogLinearInnerViewHolder(
            VhSquareImageLargeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickDayLogImg
        )

    override fun onBindViewHolder(holder: DayLogLinearInnerViewHolder, position: Int) {
        holder.bind(dayLogImages[position])
    }

    override fun getItemCount() = dayLogImages.size

    class DayLogLinearInnerViewHolder(
        private val binding: VhSquareImageLargeBinding,
        private val onClickDayLogImg: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnThrottleClickListener { onClickDayLogImg() }
        }

        fun bind(url: String) {
            binding.imageUrl = url
        }
    }
}
