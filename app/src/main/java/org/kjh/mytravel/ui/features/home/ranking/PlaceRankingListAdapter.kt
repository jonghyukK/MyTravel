package org.kjh.mytravel.ui.features.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding
import org.kjh.mytravel.model.PlaceRankingItemUiState
import org.kjh.mytravel.ui.common.setOnThrottleClickListener
import org.kjh.mytravel.utils.navigateToPlaceInfoWithDayLog

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PlaceRankingListAdapter
    : ListAdapter<PlaceRankingItemUiState, PlaceRankingListAdapter.PlaceRankingViewHolder>(PlaceRankingItemUiState.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceRankingViewHolder(
            VhPlaceRankingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceRankingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlaceRankingViewHolder(
        val binding : VhPlaceRankingItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnThrottleClickListener { view ->
                binding.rankingItem?.let { place ->
                    view.navigateToPlaceInfoWithDayLog(place.placeName)
                }
            }
        }

        fun bind(item: PlaceRankingItemUiState) {
            binding.rankingItem = item
        }
    }
}
