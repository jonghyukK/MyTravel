package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.MapSearch
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _tempSelectedPlaceItem = MutableLiveData<MapSearch>()
    val tempSelectedPlaceItem : LiveData<MapSearch> = _tempSelectedPlaceItem

    fun setTempPlaceItem(item: MapSearch) {
        _tempSelectedPlaceItem.value = item
    }
}