package org.kjh.mytravel.ui.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.kjh.mytravel.databinding.VhHomeBannerItemBinding
import org.kjh.mytravel.model.Banner

/**
 * MyTravel
 * Class: HomeBannersAdapter
 * Created by mac on 2021/12/28.
 *
 * Description:
 */

class HomeBannersAdapter(
    private val onClickBanner: (Banner) -> Unit
) : ListAdapter<Banner, HomeBannerViewHolder>(Banner.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeBannerViewHolder(
            VhHomeBannerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickBanner
        )

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position % currentList.size))
    }

    override fun getItemCount() = currentList.size * 2

    override fun getItemId(position: Int): Long {
        val expandedItems = currentList + currentList
        return expandedItems[position].bannerId.toLong()
    }
}