package org.kjh.mytravel.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.data.model.KakaoSearchPlaceModel
import org.kjh.mytravel.domain.repository.MapRepository
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
    private val _tempSelectedPlaceItem = MutableLiveData<KakaoSearchPlaceModel>()
    val tempSelectedPlaceItem : LiveData<KakaoSearchPlaceModel> = _tempSelectedPlaceItem

    fun setTempPlaceItem(item: KakaoSearchPlaceModel) {
        _tempSelectedPlaceItem.value = item
    }
}