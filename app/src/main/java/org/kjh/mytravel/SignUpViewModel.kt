package org.kjh.mytravel

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.kjh.mytravel.InputValidator.INPUT_TYPE_EMAIl
import org.kjh.mytravel.InputValidator.INPUT_TYPE_NICKNAME
import org.kjh.mytravel.InputValidator.INPUT_TYPE_PW
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidateNickName
import org.kjh.mytravel.InputValidator.isValidatePw


/**
 * MyTravel
 * Class: SignUpViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

fun <T> MediatorLiveData<T>.addSourceList(
    vararg liveDataArgument: MutableLiveData<*>,
    onChanged: () -> T
) {
    liveDataArgument.forEach {
        this.addSource(it) { value = onChanged() }
    }
}

fun String.isValidPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

class SignUpViewModel: ViewModel() {

    val email   : MutableLiveData<String> = MutableLiveData()
    val pw      : MutableLiveData<String> = MutableLiveData()
    val nickName: MutableLiveData<String> = MutableLiveData()

    private val _emailErrorState: MutableLiveData<String> = MutableLiveData()
    val emailErrorState: LiveData<String> = _emailErrorState

    private val _pwErrorState: MutableLiveData<String> = MutableLiveData()
    val pwErrorState: LiveData<String> = _pwErrorState

    private val _nickNameErrorState: MutableLiveData<String> = MutableLiveData()
    val nickNameErrorState: LiveData<String> = _nickNameErrorState

    val isValidateSignUp = MediatorLiveData<Boolean>().apply {
        addSourceList(email, pw, nickName) { isValidSignUpInfo() }
    }

    private fun isValidSignUpInfo() =
        isValidateEmail(email.value) == InputValidator.EMAIL.VALIDATE
                && isValidatePw(pw.value) == InputValidator.PW.VALIDATE
                && isValidateNickName(nickName.value) == InputValidator.NICKNAME.VALIDATE

    fun bindInputState(type: String, hasFocus: Boolean) {
        when (type) {
            INPUT_TYPE_EMAIl -> _emailErrorState.value =
                if (hasFocus) null
                else isValidateEmail(email.value).errorMsg

            INPUT_TYPE_PW -> _pwErrorState.value =
                if (hasFocus) null
                else isValidatePw(pw.value).errorMsg

            INPUT_TYPE_NICKNAME -> _nickNameErrorState.value =
                if (hasFocus) null
                else isValidateNickName(nickName.value).errorMsg
        }
    }

    fun reqSignUp() {
        Log.e("reqSignUp", """
            email   : ${email.value}
            pw      : ${pw.value}
            nickName: ${nickName.value}
        """.trimIndent())
    }
}

interface FocusEventHandler {
    fun onFocusLost(v: View, hasFocus: Boolean)
}
