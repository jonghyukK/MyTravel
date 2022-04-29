package org.kjh.mytravel.ui.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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
    private val onClickPlaceItem: (PlaceWithRanking) -> Unit
) : ListAdapter<PlaceWithRanking, PlaceRankingViewHolder>(PlaceWithRanking.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceRankingViewHolder(
            VhPlaceRankingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPlaceItem
        )

    override fun onBindViewHolder(holder: PlaceRankingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
