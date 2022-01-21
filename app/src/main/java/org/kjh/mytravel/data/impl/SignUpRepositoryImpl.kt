package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.SignUpResponse
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.SignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: SignUpRepositoryImpl
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

@Singleton
class SignUpRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val responseToSignUpResult: (Result<SignUpResponse>) -> Result<SignUpResponse>
): SignUpRepository {

    override fun makeRequestSignUp(
        email: String,
        pw   : String,
        nick : String
    ): Flow<Result<SignUpResponse>> = flow {
        emit(Result.Loading())

        try {
            val result = apiService.createUser(email, pw, nick)

            emit(responseToSignUpResult(Result.Success(result)))
        } catch (e: Exception) {
            emit(responseToSignUpResult(Result.Error(e)))
        }
    }
}