package org.kjh.mytravel.domain

/**
 * MyTravel
 * Class: Result
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
sealed class Result<T> {

    class Success<T>(val data: T): Result<T>()

    class Loading<T>: Result<T>()

    class Error<T>(val throwable: Throwable) : Result<T>()
}