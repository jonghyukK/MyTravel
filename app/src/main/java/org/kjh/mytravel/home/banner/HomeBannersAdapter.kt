package org.kjh.mytravel.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.kjh.mytravel.databinding.ItemHomeBannerBinding
import org.kjh.mytravel.uistate.BannerItemUiState

/**
 * MyTravel
 * Class: HomeBannersAdapter
 * Created by mac on 2021/12/28.
 *
 * Description:
 */

class HomeBannersAdapter
    : ListAdapter<BannerItemUiState, HomeBannerViewHolder>(BannerItemUiState.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeBannerViewHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position % currentList.size))
    }

    override fun getItemCount() = currentList.size * 2

    override fun getItemId(position: Int): Long {
        val expandedItems = currentList + currentList
        return expandedItems[position].bannerId
    }
}