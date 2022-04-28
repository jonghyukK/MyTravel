package org.kjh.mytravel.model

import org.kjh.mytravel.utils.ErrorMsg

/**
 * MyTravel
 * Class: UiState
 * Created by jonghyukkang on 2022/03/30.
 *
 * Description:
 */

// todo : ErrorCase Toast로 임시 표시 (추후 Error Case 화면 기획 및 처리 필요)

sealed class UiState<out T: Any> {
    data class Success<out T: Any>(val data: T): UiState<T>()
//    data class Error(val exception: Throwable): UiState<Nothing>()
    data class Error(val errorMsg: ErrorMsg): UiState<Nothing>()
    object Loading: UiState<Nothing>()
    object Init: UiState<Nothing>()

    fun toData(): T? = if(this is Success) this.data else null
    fun isLoading() = this is Loading
}