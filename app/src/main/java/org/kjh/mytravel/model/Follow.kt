package org.kjh.mytravel.model

import org.kjh.domain.entity.FollowEntity

/**
 * MyTravel
 * Class: Follow
 * Created by jonghyukkang on 2022/04/04.
 *
 * Description:
 */
data class Follow(
    val myProfile    : User,
    val targetProfile: User
)

fun FollowEntity.mapToPresenter() = Follow(
        myProfile.mapToPresenter(),
        targetProfile.mapToPresenter()
    )
