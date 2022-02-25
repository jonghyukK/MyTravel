package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.api.LoginApiModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: LoginDataStore
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginRemoteDataSource {
    suspend fun makeLoginRequest(email: String, pw: String): LoginApiModel
}

class LoginDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): LoginRemoteDataSource {

    override suspend fun makeLoginRequest(email: String, pw: String) =
        apiService.login(email, pw)
}