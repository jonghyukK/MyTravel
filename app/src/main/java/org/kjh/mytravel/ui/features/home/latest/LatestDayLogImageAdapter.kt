package org.kjh.mytravel.ui.features.home.latest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhSquareImageLargeBinding
import org.kjh.mytravel.ui.common.setOnThrottleClickListener

/**
 * MyTravel
 * Class: LatestDayLogImageAdapter
 * Created by jonghyukkang on 2022/05/02.
 *
 * Description:
 */

class LatestDayLogImageAdapter(
    private val onClickImg: () -> Unit,
): RecyclerView.Adapter<LatestDayLogImageAdapter.LatestDayLogImageViewHolder>() {

    private val dayLogImages = mutableListOf<String>()

    fun setItems(items: List<String>) {
        dayLogImages.clear()
        dayLogImages.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LatestDayLogImageViewHolder(
            VhSquareImageLargeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickImg
        )

    override fun onBindViewHolder(holder: LatestDayLogImageViewHolder, position: Int) {
        holder.bind(dayLogImages[position])
    }

    override fun getItemCount() = dayLogImages.size

    class LatestDayLogImageViewHolder(
        val binding   : VhSquareImageLargeBinding,
        val onClickImg: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnThrottleClickListener { onClickImg() }
        }

        fun bind(imgResource: String) {
            binding.imageUrl = imgResource
        }
    }
}