package org.kjh.mytravel.ui.features.home.ranking

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PopularRankingViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class PlaceRankingViewHolder(
    val binding       : VhPlaceRankingItemBinding,
    val onClickRanking: (String) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaceWithRanking) {
        binding.placeRanking = item

        itemView.onThrottleClick {
            onClickRanking(item.place.placeName)
        }
    }
}