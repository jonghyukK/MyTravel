package com.example.data.repository

import com.example.data.datasource.LoginDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.ApiResult
import com.example.domain.entity.Login
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: LoginRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
): LoginRepository {

    override suspend fun makeRequestLogin(
        email: String,
        pw   : String
    ): Flow<ApiResult<Login>> = flow {
        emit(ApiResult.Loading())

        val response = dataSource.makeLoginRequest(email, pw)
        emit(ResponseMapper.responseToLoginEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToLoginEntity(ApiResult.Error(it)))
    }
}