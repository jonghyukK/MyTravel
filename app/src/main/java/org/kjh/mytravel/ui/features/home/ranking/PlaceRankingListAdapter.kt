package org.kjh.mytravel.ui.features.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.utils.navigateToPlaceDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PlaceRankingListAdapter
    : ListAdapter<PlaceWithRanking, PlaceRankingListAdapter.PlaceRankingViewHolder>(PlaceWithRanking.diffCallback) {

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
            itemView.onThrottleClick { view ->
                binding.placeRanking?.place?.let { place ->
                    view.navigateToPlaceDetail(place.placeName)
                }
            }
        }

        fun bind(item: PlaceWithRanking) {
            binding.placeRanking = item
        }
    }
}
