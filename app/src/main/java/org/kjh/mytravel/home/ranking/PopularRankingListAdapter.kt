package org.kjh.mytravel.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.PlaceItem
import org.kjh.mytravel.databinding.ItemPopularPlaceBinding

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PopularRankingListAdapter(
    private val itemList: List<PlaceItem>,
    private val onClickRankingItem: (PlaceItem) -> Unit
): RecyclerView.Adapter<PopularRankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PopularRankingViewHolder(
            ItemPopularPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickRankingItem
        )


    override fun onBindViewHolder(holder: PopularRankingViewHolder, position: Int) {
        holder.bind(itemList[position], position)
    }

    override fun getItemCount() = itemList.size
}


