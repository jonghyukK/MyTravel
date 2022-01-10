package org.kjh.mytravel.home.banner

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.CityItem
import org.kjh.mytravel.databinding.ItemHomeBannerBinding

/**
 * MyTravel
 * Class: HomeBannerViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */

class HomeBannerViewHolder(
    private val binding: ItemHomeBannerBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityItem) {
        binding.cityItem = item
    }
}