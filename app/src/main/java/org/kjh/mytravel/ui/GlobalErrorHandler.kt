package org.kjh.mytravel.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MyTravel
 * Class: GlobalErrorHandler
 * Created by jonghyukkang on 2022/05/17.
 *
 * Description:
 */

class GlobalErrorHandler @Inject constructor(
    private val coroutineScope: CoroutineScope
) {
    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent = _errorEvent.asSharedFlow()

    fun sendError(errorMsg: String) {
        coroutineScope.launch {
            _errorEvent.emit(errorMsg)
        }
    }
}