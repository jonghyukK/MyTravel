package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.SignUpResponse
import javax.inject.Inject

/**
 * MyTravel
 * Class: SignUpDataSource
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */


interface SignUpDataSource {
    suspend fun makeSignUpRequest(email: String, pw: String, nickName: String): SignUpResponse
}

class SignUpDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): SignUpDataSource {

    override suspend fun makeSignUpRequest(
        email   : String,
        pw      : String,
        nickName: String
    ) = apiService.createUser(email, pw, nickName)
}