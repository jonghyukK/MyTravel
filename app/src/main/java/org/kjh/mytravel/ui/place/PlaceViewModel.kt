package org.kjh.mytravel.ui.place

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.ui.uistate.PlaceItemUiState
import org.kjh.mytravel.ui.uistate.tempPlaceItemsFlow

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

sealed class PlaceUiState {
    object Loading: PlaceUiState()
    data class Success(val placeItems: PlaceItemUiState): PlaceUiState()
    data class Error(val exception: Throwable?): PlaceUiState()
}
class PlaceViewModel(
    private val initPlaceName: String
): ViewModel() {

    private val _placeUiState = MutableStateFlow<PlaceUiState>(PlaceUiState.Loading)
    val placeUiState : StateFlow<PlaceUiState> = _placeUiState

    init {
        viewModelScope.launch {
            tempPlaceItemsFlow.collect { item ->
                val matchedItem = item.find { it.placeName == initPlaceName }

                if (matchedItem != null) {
                    _placeUiState.value = PlaceUiState.Success(matchedItem)
                } else {
                    _placeUiState.value = PlaceUiState.Error(Exception())
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        Log.e("test", "onCleared()")
    }
}