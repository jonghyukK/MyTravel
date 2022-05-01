package org.kjh.domain.entity

/**
 * MyTravel
 * Class: FollowEntity
 * Created by jonghyukkang on 2022/04/04.
 *
 * Description:
 */
data class FollowEntity(
    val myProfile: UserEntity,
    val targetProfile: UserEntity
)