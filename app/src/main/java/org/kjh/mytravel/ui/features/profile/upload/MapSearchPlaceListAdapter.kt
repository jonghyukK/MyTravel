package org.kjh.mytravel.ui.features.profile.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBsSearchPlaceBinding
import org.kjh.mytravel.model.MapQueryItem

/**
 * MyTravel
 * Class: MapSearchPlaceListAdapter
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
class MapSearchPlaceListAdapter(
    private val onClickPlace: (MapQueryItem) -> Unit
) : ListAdapter<MapQueryItem, MapSearchPlaceListAdapter.MapSearchPlaceViewHolder>(MapQueryItem.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MapSearchPlaceViewHolder(
            VhBsSearchPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPlace
        )

    override fun onBindViewHolder(holder: MapSearchPlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MapSearchPlaceViewHolder(
        val binding: VhBsSearchPlaceBinding,
        val onClickPlace: (MapQueryItem) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MapQueryItem) {
            binding.mapQueryItem = item

            itemView.setOnClickListener {
                onClickPlace(item)
            }
        }
    }
}