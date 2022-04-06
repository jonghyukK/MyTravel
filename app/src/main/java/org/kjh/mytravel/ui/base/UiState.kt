package org.kjh.mytravel.ui.base

/**
 * MyTravel
 * Class: UiState
 * Created by jonghyukkang on 2022/03/30.
 *
 * Description:
 */
sealed class UiState {
    object Init: UiState()
    object Loading: UiState()
    data class Success<T>(val data: T): UiState()
    data class Error(val error: Throwable?): UiState()
}