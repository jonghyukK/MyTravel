package org.kjh.mytravel.ui.features.profile.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBsSearchPlaceBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: MapSearchPlaceListAdapter
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
class MapSearchPlaceListAdapter(
    private val onClickQueryItem: (MapQueryItem) -> Unit
) : ListAdapter<MapQueryItem, MapSearchPlaceListAdapter.MapSearchPlaceViewHolder>(MapQueryItem.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MapSearchPlaceViewHolder(
            VhBsSearchPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickQueryItem
        )

    override fun onBindViewHolder(holder: MapSearchPlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MapSearchPlaceViewHolder(
        val binding         : VhBsSearchPlaceBinding,
        val onClickQueryItem: (MapQueryItem) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MapQueryItem) {
            binding.mapQueryItem = item

            itemView.onThrottleClick {
                onClickQueryItem(item)
            }
        }
    }
}