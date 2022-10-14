package org.kjh.mytravel.ui.features.upload.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhSearchLocationItemBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.common.setOnThrottleClickListener

/**
 * MyTravel
 * Class: MapSearchPlaceListAdapter
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
class LocationQueryResultAdapter(
    private val onClickQueryItem: (MapQueryItem) -> Unit
) : ListAdapter<MapQueryItem, LocationQueryResultAdapter.MapSearchPlaceViewHolder>(MapQueryItem.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MapSearchPlaceViewHolder(
            VhSearchLocationItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickQueryItem
        )

    override fun onBindViewHolder(holder: MapSearchPlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MapSearchPlaceViewHolder(
        val binding         : VhSearchLocationItemBinding,
        val onClickQueryItem: (MapQueryItem) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnThrottleClickListener {
                binding.mapQueryItem?.let {
                    onClickQueryItem(it)
                }
            }
        }

        fun bind(item: MapQueryItem) {
            binding.mapQueryItem = item
        }
    }
}