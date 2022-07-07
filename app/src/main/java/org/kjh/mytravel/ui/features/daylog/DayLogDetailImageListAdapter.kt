package org.kjh.mytravel.ui.features.daylog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceDetailImageItemBinding

/**
 * MyTravel
 * Class: PlaceDetailImageListAdapter
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */
class DayLogDetailImageListAdapter
    : RecyclerView.Adapter<DayLogDetailImageListAdapter.PlaceDetailImageViewHolder>() {

    private val imageItems = mutableListOf<String>()

    fun setImageItems(items: List<String>) {
        imageItems.clear()
        imageItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDetailImageViewHolder(
            VhPlaceDetailImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceDetailImageViewHolder, position: Int) {
        holder.bind(imageItems[position])
    }

    override fun getItemCount() = imageItems.size

    class PlaceDetailImageViewHolder(
        val binding: VhPlaceDetailImageItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.imageUrl = imageUrl
        }
    }
}