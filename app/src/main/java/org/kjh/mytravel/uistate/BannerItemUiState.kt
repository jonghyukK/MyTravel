package org.kjh.mytravel.uistate

import androidx.recyclerview.widget.DiffUtil
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: BannerUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class BannerItemUiState(
    val bannerId : Long,
    val bannerImg: Int
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<BannerItemUiState>() {
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

val tempBannerItems = listOf(
    BannerItemUiState(1, R.drawable.temp_img_seoul),
    BannerItemUiState(2, R.drawable.temp_img_gyeonggi),
    BannerItemUiState(3, R.drawable.temp_img_chungcheongdo),
    BannerItemUiState(4, R.drawable.temp_img_jeonrado),
    BannerItemUiState(5, R.drawable.temp_img_gangwondo),
    BannerItemUiState(6, R.drawable.temp_img_gyeongsangdo),
    BannerItemUiState(7, R.drawable.temp_img_jeju)
)