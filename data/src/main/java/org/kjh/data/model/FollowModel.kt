package org.kjh.data.model

import org.kjh.domain.entity.FollowEntity

/**
 * MyTravel
 * Class: FollowApiModel
 * Created by jonghyukkang on 2022/04/04.
 *
 * Description:
 */
data class FollowModel(
    val myProfile    : UserModel,
    val targetProfile: UserModel
)

fun FollowModel.mapToDomain() =
    FollowEntity(
        myProfile = myProfile.mapToDomain(),
        targetProfile = targetProfile.mapToDomain()
    )