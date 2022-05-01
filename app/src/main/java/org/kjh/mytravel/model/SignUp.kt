package org.kjh.mytravel.model

import org.kjh.domain.entity.SignUpEntity

/**
 * MyTravel
 * Class: SignUp
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class SignUp(
    val isRegistered: Boolean,
    val errorMsg    : String? = null,
)

fun SignUpEntity.mapToPresenter() =
    SignUp(isRegistered, errorMsg)