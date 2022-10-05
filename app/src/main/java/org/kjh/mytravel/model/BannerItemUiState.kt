package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.BannerEntity

/**
 * MyTravel
 * Class: Banner
 * Created by jonghyukkang on 2022/03/04.
 *
 * Description:
 */
data class BannerItemUiState(
    val bannerId   : Int,
    val bannerImg  : String,
    val bannerTitle: String,
    val bannerTopic: String
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<BannerItemUiState>() {
            override fun areItemsTheSame(
                oldItem: BannerItemUiState,
                newItem: BannerItemUiState
            ): Boolean =
                oldItem.bannerId == newItem.bannerId

            override fun areContentsTheSame(
                oldItem: BannerItemUiState,
                newItem: BannerItemUiState
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun BannerEntity.mapToPresenter() =
    BannerItemUiState(bannerId, bannerImg, bannerTitle, bannerTopic)