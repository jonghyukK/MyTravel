package org.kjh.domain.entity

/**
 * MyTravel
 * Class: Result
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

sealed class ApiResult<out T: Any> {

    object Loading: ApiResult<Nothing>()

    data class Success<out T: Any>(val data: T): ApiResult<T>()

    data class Error(val throwable: Throwable): ApiResult<Nothing>()
}