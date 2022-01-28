package org.kjh.mytravel.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MyTravel
 * Class: NotLoginViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

class NotLoginViewModel: ViewModel() {

    private val _isSuccessLoginOrSignUp = MutableLiveData<Boolean>()
    val isSuccessLoginOrSignUp: LiveData<Boolean> = _isSuccessLoginOrSignUp

    fun setLoginOrSignUpState() {
        _isSuccessLoginOrSignUp.value = true
    }
}