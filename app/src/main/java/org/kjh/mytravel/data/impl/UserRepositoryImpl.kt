package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.UserResponse
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: UserRepositoryImpl
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */

@Singleton
class UserRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val responseToUserResult: (Result<UserResponse>) -> Result<UserResponse>
): UserRepository {

    override fun getUserInfo(email: String)
    : Flow<Result<UserResponse>> = flow {
        emit(Result.Loading())

        try {
            val result = apiService.getUser(email)

            emit(responseToUserResult(Result.Success(result)))
        } catch (e: Exception) {
            emit(responseToUserResult(Result.Error(e)))
        }
    }
}