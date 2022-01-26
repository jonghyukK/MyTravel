package org.kjh.mytravel.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kjh.mytravel.domain.repository.PlaceRepository
import org.kjh.mytravel.domain.usecase.GetPlaceListUseCase
import javax.inject.Inject

/**
 * MyTravel
 * Class: PlaceViewModelFactory
 * Created by mac on 2022/01/17.
 *
 * Description:
 */
//class PlaceViewModelFactory(
//    private val initPlaceName: String
//): ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        PlaceViewModel(initPlaceName) as T
//}