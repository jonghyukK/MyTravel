package org.kjh.mytravel.ui.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.kjh.mytravel.databinding.ItemPopularPlaceBinding
import org.kjh.mytravel.ui.uistate.RankingItemUiState

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PopularRankingListAdapter(
    private val onClickRankingItem: (RankingItemUiState) -> Unit
) : ListAdapter<RankingItemUiState, PopularRankingViewHolder>(RankingItemUiState.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PopularRankingViewHolder(
            ItemPopularPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickRankingItem
        )


    override fun onBindViewHolder(holder: PopularRankingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


