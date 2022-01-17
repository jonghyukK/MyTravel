package org.kjh.mytravel.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kjh.mytravel.ui.place.PlaceViewModel

/**
 * MyTravel
 * Class: UserViewModelFactory
 * Created by mac on 2022/01/17.
 *
 * Description:
 */
class UserViewModelFactory(
    private val initUserEmail: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserViewModel(initUserEmail) as T
}