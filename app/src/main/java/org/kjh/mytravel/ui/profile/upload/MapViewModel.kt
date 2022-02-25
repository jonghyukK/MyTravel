package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.kjh.mytravel.model.MapQueryItem
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {
    private val _tempSelectedPlaceItem = MutableLiveData<MapQueryItem>()
    val tempSelectedPlaceItem : LiveData<MapQueryItem> = _tempSelectedPlaceItem

    fun setTempPlaceItem(item: MapQueryItem) {
        _tempSelectedPlaceItem.value = item
    }
}