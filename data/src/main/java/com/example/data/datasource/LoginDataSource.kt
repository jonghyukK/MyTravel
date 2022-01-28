package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.LoginResponse
import javax.inject.Inject

/**
 * MyTravel
 * Class: LoginDataStore
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginDataSource {
    suspend fun makeLoginRequest(email: String, pw: String): LoginResponse
}

class LoginDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): LoginDataSource {

    override suspend fun makeLoginRequest(email: String, pw: String) =
        apiService.login(email, pw)
}