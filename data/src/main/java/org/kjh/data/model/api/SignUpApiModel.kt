package org.kjh.data.model.api

import org.kjh.domain.entity.SignUpEntity
import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: SignUpModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class SignUpApiModel(
    @SerializedName("result")
    val isRegistered: Boolean,
    val errorMsg    : String? = null
)

fun SignUpApiModel.mapToDomain() =
    SignUpEntity(isRegistered, errorMsg)


