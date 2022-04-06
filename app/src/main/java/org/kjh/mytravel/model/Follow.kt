package org.kjh.mytravel.model

/**
 * MyTravel
 * Class: Follow
 * Created by jonghyukkang on 2022/04/04.
 *
 * Description:
 */
data class Follow(
    val myProfile: User,
    val targetProfile: User
)