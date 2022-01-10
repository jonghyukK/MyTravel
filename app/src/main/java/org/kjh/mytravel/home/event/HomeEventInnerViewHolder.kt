package org.kjh.mytravel.home.event

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.PlaceItem
import org.kjh.mytravel.databinding.ItemEventInnerListBinding

/**
 * MyTravel
 * Class: HomeEventInnerViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class CityListHorizontalViewHolder(
    private val binding: ItemEventInnerListBinding,
    private val onClickItem: (PlaceItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(placeItem: PlaceItem) {
        binding.placeItem = placeItem
        binding.clEventListContainer.setOnClickListener {
            onClickItem(placeItem)
        }
    }
}