package org.kjh.mytravel.ui.place.subcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlacesBySubCityRowBinding
import org.kjh.mytravel.model.Place

/**
 * MyTravel
 * Class: PlaceListByCityNameAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityListAdapter(
    private val onClickPlaceItem: (String) -> Unit
) : ListAdapter<Place, PlacesBySubCityViewHolder>(Place.diffCallback) {

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