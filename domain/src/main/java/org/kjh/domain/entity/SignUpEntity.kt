package org.kjh.domain.entity

/**
 * MyTravel
 * Class: model
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */

data class SignUpEntity(
    val isRegistered: Boolean,
    val errorMsg    : String? = null,
)