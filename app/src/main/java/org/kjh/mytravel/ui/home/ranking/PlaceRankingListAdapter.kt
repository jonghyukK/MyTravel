package org.kjh.mytravel.ui.home.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.entity.Place
import com.example.domain.entity.PlaceRanking
import org.kjh.mytravel.databinding.VhPlaceRankingItemBinding

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PlaceRankingListAdapter(
    private val onClickPlaceItem: (PlaceRanking) -> Unit
) : ListAdapter<PlaceRanking, PlaceRankingViewHolder>(PlaceRankingDiffUtil) {

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

object PlaceRankingDiffUtil: DiffUtil.ItemCallback<PlaceRanking>() {
    override fun areItemsTheSame(
        oldItem: PlaceRanking,
        newItem: PlaceRanking
    ) = oldItem.place.placeId == newItem.place.placeId

    override fun areContentsTheSame(
        oldItem: PlaceRanking,
        newItem: PlaceRanking
    ) = oldItem == newItem
}


