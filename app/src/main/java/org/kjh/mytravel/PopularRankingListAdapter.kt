package org.kjh.mytravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemPopularPlaceBinding

/**
 * MyTravel
 * Class: PopularRankingListAdapter
 * Created by mac on 2022/01/04.
 *
 * Description:
 */
class PopularRankingListAdapter(
    private val itemList: List<CityItem>
): RecyclerView.Adapter<PopularRankingListAdapter.PopularRankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularRankingViewHolder {
        return PopularRankingViewHolder(
            ItemPopularPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularRankingViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    class PopularRankingViewHolder(
        val binding: ItemPopularPlaceBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CityItem) {
            binding.cityItem = item
        }
    }
}


