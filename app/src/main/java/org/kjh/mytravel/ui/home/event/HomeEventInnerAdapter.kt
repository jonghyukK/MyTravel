package org.kjh.mytravel.ui.home.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemEventInnerListBinding
import org.kjh.mytravel.ui.uistate.PlaceItemUiState

/**
 * MyTravel
 * Class: HomeEventListAdapter
 * Created by mac on 2022/01/03.
 *
 * Description:
 */


class HomeEventInnerAdapter(
    private val eventInnerList: List<PlaceItemUiState>,
    private val onClickItem: (PlaceItemUiState) -> Unit
) : RecyclerView.Adapter<CityListHorizontalViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CityListHorizontalViewHolder(
        ItemEventInnerListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), onClickItem
    )

    override fun onBindViewHolder(holder: CityListHorizontalViewHolder, position: Int) {
        holder.bind(eventInnerList[position])
    }

    override fun getItemCount() = eventInnerList.size
}