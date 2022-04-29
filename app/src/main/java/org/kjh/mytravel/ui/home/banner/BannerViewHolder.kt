package org.kjh.mytravel.ui.home.banner

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhHomeBannerItemBinding
import org.kjh.mytravel.model.Banner

/**
 * MyTravel
 * Class: HomeBannerViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */

class BannerViewHolder(
    private val binding: VhHomeBannerItemBinding,
    private val onClickBanner: (Banner) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Banner) {
        binding.bannerItem = item
        itemView.setOnClickListener {
            onClickBanner(item)
        }
    }
}