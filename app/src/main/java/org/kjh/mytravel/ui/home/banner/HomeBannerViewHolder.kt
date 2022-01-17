package org.kjh.mytravel.ui.home.banner

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemHomeBannerBinding
import org.kjh.mytravel.ui.uistate.BannerItemUiState

/**
 * MyTravel
 * Class: HomeBannerViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */

class HomeBannerViewHolder(
    private val binding: ItemHomeBannerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BannerItemUiState) {
        binding.bannerItemUiState = item
    }
}