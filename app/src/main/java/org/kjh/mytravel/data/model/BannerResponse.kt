package org.kjh.mytravel.data.model

import org.kjh.mytravel.ui.home.BannerUiState
import org.kjh.mytravel.ui.uistate.BannerItemUiState

/**
 * MyTravel
 * Class: BannerResponse
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

data class BannerResponse(
    val bannerList: List<BannerItemUiState>
)