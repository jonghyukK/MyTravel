package org.kjh.data.model

import org.kjh.domain.entity.SignUpEntity

/**
 * MyTravel
 * Class: SignUpModel
 * Created by jonghyukkang on 2022/05/10.
 *
 * Description:
 */
data class SignUpModel(
    val isSuccess     : Boolean,
    val signUpErrorMsg: String?
)

fun SignUpModel.mapToDomain() =
    SignUpEntity(isSuccess, signUpErrorMsg)