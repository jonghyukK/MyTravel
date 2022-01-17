package org.kjh.mytravel.ui.uistate

import androidx.recyclerview.widget.DiffUtil
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: CityItemUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class CityCategoryItemUiState(
    val cityId  : Long,
    val cityName: String,
    val cityDesc: String,
    val cityImg : Int
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<CityCategoryItemUiState>() {
            override fun areItemsTheSame(
                oldItem: CityCategoryItemUiState,
                newItem: CityCategoryItemUiState
            ): Boolean =
                oldItem.cityId == newItem.cityId

            override fun areContentsTheSame(
                oldItem: CityCategoryItemUiState,
                newItem: CityCategoryItemUiState
            ): Boolean =
                oldItem == newItem
        }
    }
}

val tempCityCategoryItems = listOf(
    CityCategoryItemUiState(1, "서울", "대한민국의 수도 서울!", R.drawable.temp_img_seoul),
    CityCategoryItemUiState(2, "경기도", "서울을 둘러싸고 있는 경기도!", R.drawable.temp_img_gyeonggi),
    CityCategoryItemUiState(3, "충청도", "여기는 충청도!", R.drawable.temp_img_chungcheongdo),
    CityCategoryItemUiState(4, "전라도", "여기는 전라도!", R.drawable.temp_img_jeonrado),
    CityCategoryItemUiState(5, "강원도", "여기는 강원도!", R.drawable.temp_img_gangwondo),
    CityCategoryItemUiState(6, "경상도", "여기는 경상도!", R.drawable.temp_img_gyeongsangdo),
    CityCategoryItemUiState(7, "제주도", "여기는 제주도!", R.drawable.temp_img_jeju)
)


