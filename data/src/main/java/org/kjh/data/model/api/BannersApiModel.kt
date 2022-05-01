package org.kjh.data.model.api

import org.kjh.data.model.BannerModel

/**
 * MyTravel
 * Class: BannersApiModel
 * Created by jonghyukkang on 2022/03/04.
 *
 * Description:
 */
data class BannersApiModel(
    val result: Boolean,
    val data : List<BannerModel>,
    val errorMsg: String? = null
)