package org.kjh.mytravel.ui.features.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhHomeBannerItemBinding
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.ui.features.home.HomeFragmentDirections
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: HomeBannersAdapter
 * Created by mac on 2021/12/28.
 *
 * Description:
 */

class BannerListAdapter
    : ListAdapter<Banner, BannerListAdapter.BannerViewHolder>(Banner.diffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BannerViewHolder(
            VhHomeBannerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position % currentList.size))
    }

    override fun getItemCount() = currentList.size * 2

    override fun getItemId(position: Int): Long {
        val expandedItems = currentList + currentList
        return expandedItems[position].bannerId.toLong()
    }

    class BannerViewHolder(
        private val binding : VhHomeBannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.bannerItem?.bannerTopic?.let { bannerTopic ->
                    view.navigateTo(
                        HomeFragmentDirections.actionToPlacesBySubCity(bannerTopic))
                }
            }
        }

        fun bind(item: Banner) {
            binding.bannerItem = item
        }
    }
}