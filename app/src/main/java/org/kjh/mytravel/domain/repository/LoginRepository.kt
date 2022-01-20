package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.LoginResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: LoginRepository
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
interface LoginRepository {
    fun makeRequestLogin(
        email: String,
        pw   : String
    ): Flow<Result<LoginResponse>>
}