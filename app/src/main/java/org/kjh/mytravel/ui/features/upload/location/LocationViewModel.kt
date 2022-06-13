package org.kjh.mytravel.ui.features.upload.location

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class LocationViewModel @Inject constructor(): ViewModel() {
    private val _selectedLocationItem: MutableStateFlow<MapQueryItem?> = MutableStateFlow(null)
    val selectedLocationItem = _selectedLocationItem.asStateFlow()

    fun setTempPlaceItem(item: MapQueryItem) {
        _selectedLocationItem.value = item
    }
}