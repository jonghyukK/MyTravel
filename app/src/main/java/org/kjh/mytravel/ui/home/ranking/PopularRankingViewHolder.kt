package org.kjh.mytravel.ui.home.ranking

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemPopularPlaceBinding
import org.kjh.mytravel.ui.uistate.RankingItemUiState

/**
 * MyTravel
 * Class: PopularRankingViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class PopularRankingViewHolder(
    val binding: ItemPopularPlaceBinding,
    val onClickRanking: (RankingItemUiState) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RankingItemUiState) {
        binding.rankingItemUiState = item

        binding.cvRankingContainer.setOnClickListener {
            onClickRanking(item)
        }
    }
}