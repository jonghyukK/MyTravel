package org.kjh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.kjh.data.datasource.LoginRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.LoginEntity
import org.kjh.domain.repository.LoginRepository
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