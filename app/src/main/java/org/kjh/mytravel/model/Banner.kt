package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import com.example.domain2.entity.BannerEntity

/**
 * MyTravel
 * Class: Banner
 * Created by jonghyukkang on 2022/03/04.
 *
 * Description:
 */
data class Banner(
    val bannerId   : Int,
    val bannerImg  : String,
    val bannerTitle: String,
    val bannerTopic: String
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Banner>() {
            override fun areItemsTheSame(
                oldItem: Banner,
                newItem: Banner
            ): Boolean =
                oldItem.bannerId == newItem.bannerId

            override fun areContentsTheSame(
                oldItem: Banner,
                newItem: Banner
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun BannerEntity.mapToPresenter() =
    Banner(bannerId, bannerImg, bannerTitle, bannerTopic)