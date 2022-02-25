package com.example.domain2.entity

/**
 * MyTravel
 * Class: Result
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
sealed class ApiResult<T> {

    class Success<T>(val data: T): ApiResult<T>()

    class Loading<T>: ApiResult<T>()

    class Error<T>(val throwable: Throwable) : ApiResult<T>()
}