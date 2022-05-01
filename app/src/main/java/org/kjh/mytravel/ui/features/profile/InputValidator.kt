package org.kjh.mytravel.ui.features.profile

import org.kjh.mytravel.ui.features.profile.signup.isValidPattern

/**
 * MyTravel
 * Class: InputValidator
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

object InputValidator {
    const val INPUT_TYPE_EMAIl      = "email"
    const val INPUT_TYPE_PW         = "pw"
    const val INPUT_TYPE_NICKNAME   = "nickName"

    fun isValidateEmail(email: String?) =
        if (email.isNullOrBlank()) {
            EMAIL.ERROR_EMPTY
        } else if (!email.isValidPattern()) {
            EMAIL.ERROR_PATTERN
        } else {
            EMAIL.VALIDATE
        }

    fun isValidatePw(pw: String?) = when {
        pw.isNullOrBlank() ->
            PW.ERROR_EMPTY
        pw.length < 8 ->
            PW.ERROR_LESS_THEN_8
        else ->
            PW.VALIDATE
    }

    fun isValidateNickName(nickName: String?) =
        if (nickName.isNullOrBlank()) {
            NICKNAME.ERROR_EMPTY
        } else {
            NICKNAME.VALIDATE
        }

    enum class EMAIL(val errorMsg: String?) {
        VALIDATE(null),
        ERROR_EMPTY("이메일을 입력해 주세요."),
        ERROR_PATTERN("이메일 형식이 올바르지 않습니다."),
        ERROR_NOT_EXIST("가입된 이메일이 아닙니다.")
    }

    enum class PW(val errorMsg: String?) {
        VALIDATE(null),
        ERROR_EMPTY("비밀번호를 입력해 주세요."),
        ERROR_LESS_THEN_8("8자리 이상 입력해 주세요."),
        ERROR_WRONG_PW("비밀번호가 맞지 않습니다.")
    }

    enum class NICKNAME(val errorMsg: String?) {
        VALIDATE(null),
        ERROR_EMPTY("닉네임을 입력해 주세요.")
    }
}