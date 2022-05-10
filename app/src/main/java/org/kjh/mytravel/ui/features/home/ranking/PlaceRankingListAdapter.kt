package org.kjh.mytravel.ui.features.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding
import org.kjh.mytravel.model.PlaceWithRanking

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PlaceRankingListAdapter(
    private val onClickRanking: (String) -> Unit
) : ListAdapter<PlaceWithRanking, PlaceRankingViewHolder>(PlaceWithRanking.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceRankingViewHolder(
            VhPlaceRankingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickRanking
        )

    override fun onBindViewHolder(holder: PlaceRankingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
