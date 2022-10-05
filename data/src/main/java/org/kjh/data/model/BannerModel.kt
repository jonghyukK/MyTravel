package org.kjh.data.model

import org.kjh.domain.entity.BannerEntity

/**
 * MyTravel
 * Class: BannerModel
 * Created by jonghyukkang on 2022/03/04.
 *
 * Description:
 */
data class BannerModel(
    val bannerId   : Int,
    val bannerImg  : String,
    val bannerTitle: String,
    val bannerTopic: String
)

fun BannerModel.mapToDomain() =
    BannerEntity(bannerId, bannerImg, bannerTitle, bannerTopic)
