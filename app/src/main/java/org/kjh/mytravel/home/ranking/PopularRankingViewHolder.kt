package org.kjh.mytravel.home.ranking

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.PlaceItem
import org.kjh.mytravel.databinding.ItemPopularPlaceBinding

/**
 * MyTravel
 * Class: PopularRankingViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class PopularRankingViewHolder(
    val binding: ItemPopularPlaceBinding,
    val onClickRanking: (PlaceItem) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaceItem, position: Int) {
        binding.placeItem = item
        binding.ranking = position + 1

        binding.cvRankingContainer.setOnClickListener {
            onClickRanking(item)
        }
    }
}