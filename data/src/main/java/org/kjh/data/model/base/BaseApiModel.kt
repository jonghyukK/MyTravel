package org.kjh.data.model.base

/**
 * MyTravel
 * Class: BaseApiModel
 * Created by jonghyukkang on 2022/05/06.
 *
 * Description:
 */

data class BaseApiModel<out T: Any>(
    val result  : Boolean,
    val errorMsg: String? = null,
    val data    : T
)