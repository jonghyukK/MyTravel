package org.kjh.mytravel.ui.features.place.subcity

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlacesBySubCityRowBinding
import org.kjh.mytravel.model.Place

/**
 * MyTravel
 * Class: PlacesBySubCityViewHolder
 * Created by jonghyukkang on 2022/04/29.
 *
 * Description:
 */

class PlacesBySubCityViewHolder(
    private val binding: VhPlacesBySubCityRowBinding,
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

        itemView.setOnClickListener {
            onClickPlaceItem(item.placeName)
        }
    }
}
