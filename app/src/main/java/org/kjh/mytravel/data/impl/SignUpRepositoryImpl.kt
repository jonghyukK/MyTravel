package org.kjh.mytravel.data.impl

import android.util.Log
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

        val result = apiService.createUser(email, pw, "this is token")

        Log.e("signup", "$result")
        val response = SignUpResponse(
            isRegistered = true,
            errorMsg = null
        )

        val str = Result.Success(response)
        emit(responseToSignUpResult(str))
    }
}