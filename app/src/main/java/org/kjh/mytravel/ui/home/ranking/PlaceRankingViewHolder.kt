package org.kjh.mytravel.ui.home.ranking

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.PlaceRanking
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding

/**
 * MyTravel
 * Class: PopularRankingViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class PlaceRankingViewHolder(
    val binding: VhPlaceRankingItemBinding,
    val onClickPlaceItem: (PlaceRanking) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaceRanking) {
        binding.placeRanking = item

        itemView.setOnClickListener {
            onClickPlaceItem(item)
        }
    }
}