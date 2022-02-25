package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.api.SignUpApiModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: SignUpDataSource
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */


interface SignUpRemoteDataSource {
    suspend fun makeSignUpRequest(email: String, pw: String, nickName: String): SignUpApiModel
}

class SignUpDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): SignUpRemoteDataSource {

    override suspend fun makeSignUpRequest(
        email   : String,
        pw      : String,
        nickName: String
    ) = apiService.createUser(email, pw, nickName)
}