package org.kjh.mytravel.ui.home.ranking

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding
import org.kjh.mytravel.model.PlaceWithRanking

/**
 * MyTravel
 * Class: PopularRankingViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class PlaceRankingViewHolder(
    val binding: VhPlaceRankingItemBinding,
    val onClickPlaceItem: (PlaceWithRanking) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaceWithRanking) {
        binding.placeRanking = item

        itemView.setOnClickListener {
            onClickPlaceItem(item)
        }
    }
}