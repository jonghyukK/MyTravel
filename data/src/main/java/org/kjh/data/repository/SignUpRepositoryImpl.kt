package org.kjh.data.repository

import org.kjh.data.datasource.SignUpRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.SignUpEntity
import org.kjh.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: SignUpRepositoryImpl
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
class SignUpRepositoryImpl @Inject constructor(
    private val dataSource: SignUpRemoteDataSource
): SignUpRepository {

    override suspend fun makeRequestSignUp(
        email: String,
        pw   : String,
        nick : String
    ): Flow<ApiResult<SignUpEntity>> = flow {
        emit(ApiResult.Loading)

        val response = dataSource.makeSignUpRequest(email, pw, nick)
        emit(ResponseMapper.responseToSignUpEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToSignUpEntity(ApiResult.Error(it)))
    }
}