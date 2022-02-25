package com.example.data.repository

import com.example.data.datasource.LoginRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.LoginEntity
import com.example.domain2.repository.LoginRepository
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
    private val dataSource: LoginRemoteDataSource
): LoginRepository {

    override suspend fun makeRequestLogin(
        email: String,
        pw   : String
    ): Flow<ApiResult<LoginEntity>> = flow {
        emit(ApiResult.Loading())

        val response = dataSource.makeLoginRequest(email, pw)
        emit(ResponseMapper.responseToLoginEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToLoginEntity(ApiResult.Error(it)))
    }
}