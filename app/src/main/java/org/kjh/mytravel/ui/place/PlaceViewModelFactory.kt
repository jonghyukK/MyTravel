package org.kjh.mytravel.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * MyTravel
 * Class: PlaceViewModelFactory
 * Created by mac on 2022/01/17.
 *
 * Description:
 */
class PlaceViewModelFactory(
    private val initPlaceName: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlaceViewModel(initPlaceName) as T
}