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
    val isSuccess     : Boolean,
    val signUpErrorMsg: String? = null,
)

fun SignUpEntity.mapToPresenter() =
    SignUp(isSuccess, signUpErrorMsg)