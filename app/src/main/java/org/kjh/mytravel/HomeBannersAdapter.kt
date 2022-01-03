package org.kjh.mytravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemHomeBannerBinding

/**
 * MyTravel
 * Class: HomeBannersAdapter
 * Created by mac on 2021/12/28.
 *
 * Description:
 */

class HomeBannersAdapter(
    private val bannerList: List<CityItem>
): RecyclerView.Adapter<HomeBannersAdapter.HomeBannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeBannerViewHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(bannerList[position % bannerList.size])
    }

    override fun getItemCount() = bannerList.size * 2

    override fun getItemId(position: Int): Long {
        val itemId = bannerList + bannerList
        return itemId[position].id
    }

    class HomeBannerViewHolder(
        private val binding: ItemHomeBannerBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CityItem) {
            binding.cityItem = item
        }
    }
}