package org.kjh.mytravel.place

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.kjh.mytravel.uistate.PlaceItemUiState
import org.kjh.mytravel.uistate.tempPlaceItemList

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
class PlaceViewModel: ViewModel() {

    private val _placeData = MutableLiveData<PlaceItemUiState>()
    val placeData: LiveData<PlaceItemUiState> = _placeData

    fun getPlaceData(placeName: String) {
        viewModelScope.launch {
            _placeData.value = tempPlaceItemList.find { it.placeName == placeName }
        }
    }

    override fun onCleared() {
        super.onCleared()

        Log.e("test", "onCleared()")
    }
}