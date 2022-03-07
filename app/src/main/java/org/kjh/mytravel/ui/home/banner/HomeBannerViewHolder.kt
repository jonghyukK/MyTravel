package org.kjh.mytravel.ui.home.banner

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhHomeBannerItemBinding
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.ui.uistate.BannerItemUiState

/**
 * MyTravel
 * Class: HomeBannerViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */

class HomeBannerViewHolder(
    private val binding: VhHomeBannerItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Banner) {
        binding.bannerItem = item
    }
}