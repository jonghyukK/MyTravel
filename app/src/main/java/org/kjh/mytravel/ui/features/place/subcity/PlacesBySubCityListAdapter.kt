package org.kjh.mytravel.ui.features.place.subcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlacesBySubCityRowBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceListByCityNameAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityListAdapter(
    private val onClickPlaceItem: (String) -> Unit
) : ListAdapter<Place, PlacesBySubCityListAdapter.PlacesBySubCityViewHolder>(Place.diffCallback) {

    class PlacesBySubCityViewHolder(
        private val binding         : VhPlacesBySubCityRowBinding,
        private val onClickPlaceItem: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        private val postListAdapter = PlacesBySubCityPostListAdapter {
            onClickPlaceItem(it.placeName)
        }

        init {
            binding.rvPostList.apply {
                adapter = postListAdapter
                setHasFixedSize(true)
                addItemDecoration(PlacesBySubCityPostItemDecoration())
            }
        }

        fun bind(item: Place) {
            binding.placeItem = item
            postListAdapter.submitList(item.posts)

            itemView.onThrottleClick {
                onClickPlaceItem(item.placeName)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PlacesBySubCityViewHolder(
        VhPlacesBySubCityRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), onClickPlaceItem
    )

    override fun onBindViewHolder(holder: PlacesBySubCityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}