package org.kjh.domain.entity

/**
 * MyTravel
 * Class: BannerEntity
 * Created by jonghyukkang on 2022/03/04.
 *
 * Description:
 */
data class BannerEntity(
    val bannerId   : Int,
    val bannerImg  : String,
    val bannerTitle: String,
    val bannerTopic: String
)