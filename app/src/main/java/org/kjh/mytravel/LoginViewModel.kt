package org.kjh.mytravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidatePw

/**
 * MyTravel
 * Class: LoginViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */
class LoginViewModel: ViewModel() {

    val email   : MutableLiveData<String> = MutableLiveData()
    val pw      : MutableLiveData<String> = MutableLiveData()

    private val _emailErrorState: MutableLiveData<String> = MutableLiveData()
    val emailErrorState: LiveData<String> = _emailErrorState

    private val _pwErrorState: MutableLiveData<String> = MutableLiveData()
    val pwErrorState: LiveData<String> = _pwErrorState

    val isValidateLogin = MediatorLiveData<Boolean>().apply {
        addSourceList(email, pw) { isValidLogin() }
    }

    private fun isValidLogin(): Boolean {
        return isValidateEmail(email.value) == InputValidator.EMAIL.VALIDATE
                && isValidatePw(pw.value) == InputValidator.PW.VALIDATE
    }

    fun bindInputState(type: String, hasFocus: Boolean) {
        when (type) {
            InputValidator.INPUT_TYPE_EMAIl -> _emailErrorState.value =
                if (hasFocus) null
                else isValidateEmail(email.value).errorMsg

            InputValidator.INPUT_TYPE_PW -> _pwErrorState.value =
                if (hasFocus) null
                else isValidatePw(pw.value).errorMsg
        }
    }

    fun reqLogin() {
        Log.e("reqLogin", """
            email   : ${email.value}
            pw      : ${pw.value}
           
        """.trimIndent())

        _emailErrorState.value = InputValidator.EMAIL.ERROR_NOT_EXIST.errorMsg
    }
}