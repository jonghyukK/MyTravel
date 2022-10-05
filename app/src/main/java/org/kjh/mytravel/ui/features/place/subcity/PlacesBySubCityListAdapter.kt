package org.kjh.mytravel.ui.features.place.subcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlacesBySubCityRowBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.utils.navigateToPlaceInfoWithDayLog
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceListByCityNameAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityListAdapter
    : ListAdapter<Place, PlacesBySubCityListAdapter.PlacesBySubCityViewHolder>(Place.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlacesBySubCityViewHolder(
            VhPlacesBySubCityRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun onBindViewHolder(holder: PlacesBySubCityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlacesBySubCityViewHolder(
        private val binding : VhPlacesBySubCityRowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val dayLogListAdapter = PlacesBySubCityDayLogListAdapter()

        init {
            binding.dayLogImgRecyclerView.apply {
                setHasFixedSize(true)
                adapter = dayLogListAdapter
                addItemDecoration(PlacesBySubCityDayLogItemDecoration())
                addOnItemTouchListener(OnNestedHorizontalTouchListener())
            }

            itemView.onThrottleClick { view ->
                binding.placeItem?.let { place ->
                    view.navigateToPlaceInfoWithDayLog(place.placeName)
                }
            }
        }

        fun bind(item: Place) {
            binding.placeItem = item
        }
    }
}