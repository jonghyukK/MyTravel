package org.kjh.mytravel.utils

import android.util.Patterns

/**
 * MyTravel
 * Class: InputValidator
 * Created by mac on 2022/01/13.
 *
 * Description:
 */


object InputValidator {

    private const val TYPE_EMAIL = "email"
    private const val TYPE_PW    = "pw"
    private const val TYPE_NICKNAME = "nickname"

    fun isValidateEmail(email: String) =
        checkEmailValidate(email) == Email.VALIDATE

    fun isValidatePw(pw: String) =
        checkPwValidate(pw) == Pw.VALIDATE

    fun isValidateNickname(nickName: String) =
        checkNicknameValidate(nickName) == Nickname.VALIDATE

    fun getErrorMsgOrNull(tag: String, inputText: String) = when (tag) {
        TYPE_EMAIL -> checkEmailValidate(inputText).errorMsg
        TYPE_PW -> checkPwValidate(inputText).errorMsg
        TYPE_NICKNAME -> checkNicknameValidate(inputText).errorMsg
        else -> "Nothing tag type"
    }

    private fun checkEmailValidate(email: String?) = when {
        email.isNullOrBlank() -> Email.ERROR_EMPTY
        !email.isValidEmailPattern() -> Email.ERROR_PATTERN
        else -> Email.VALIDATE
    }

    private fun checkPwValidate(pw: String?) = when {
        pw.isNullOrBlank() -> Pw.ERROR_EMPTY
        pw.length < 8 -> Pw.ERROR_LESS_THEN_8
        else -> Pw.VALIDATE
    }

    private fun checkNicknameValidate(nickName: String?) = when {
        nickName.isNullOrBlank() -> Nickname.ERROR_EMPTY
        else -> Nickname.VALIDATE
    }

    enum class Email(val errorMsg: String?) {
        VALIDATE(null),
        ERROR_EMPTY("이메일을 입력해 주세요."),
        ERROR_PATTERN("이메일 형식이 올바르지 않습니다."),
        ERROR_EXIST_EMAIL("이미 가입된 이메일입니다."),
        ERROR_NOT_EXIST_EMAIL("가입되지 않은 이메일입니다.")
    }

    enum class Pw(val errorMsg: String?) {
        VALIDATE(null),
        ERROR_EMPTY("비밀번호를 입력해 주세요."),
        ERROR_LESS_THEN_8("8자리 이상 입력해 주세요."),
        ERROR_WRONG_PW("비밀번호가 올바르지 않습니다.")
    }

    enum class Nickname(val errorMsg: String?) {
        VALIDATE(null),
        ERROR_EMPTY("닉네임을 입력해 주세요.")
    }
}

private fun String.isValidEmailPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()