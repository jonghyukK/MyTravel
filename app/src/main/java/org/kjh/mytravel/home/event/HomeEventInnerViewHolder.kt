package org.kjh.mytravel.home.event

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemEventInnerListBinding
import org.kjh.mytravel.uistate.PlaceItemUiState

/**
 * MyTravel
 * Class: HomeEventInnerViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class CityListHorizontalViewHolder(
    private val binding: ItemEventInnerListBinding,
    private val onClickItem: (PlaceItemUiState) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(placeItem: PlaceItemUiState) {
        binding.placeItemUiState = placeItem
        binding.clEventListContainer.setOnClickListener {
            onClickItem(placeItem)
        }
    }
}