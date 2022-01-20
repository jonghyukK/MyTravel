package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.LoginResponse
import org.kjh.mytravel.domain.repository.LoginRepository
import org.kjh.mytravel.domain.Result
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: LoginRepositoryImpl
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToLoginResult: (Result<LoginResponse>) -> Result<LoginResponse>
): LoginRepository {

    override fun makeRequestLogin(
        email: String,
        pw: String
    ): Flow<Result<LoginResponse>> = flow {
        val response = LoginResponse(
            isLoggedIn = true,
            errorMsg = null
        )

        val str = Result.Success(response)
        emit(responseToLoginResult(str))
    }
}