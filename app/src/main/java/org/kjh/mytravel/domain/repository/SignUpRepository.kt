package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.SignUpResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: SignUpRepository
 * Created by mac on 2022/01/17.
 *
 * Description:
 */
interface SignUpRepository {
    fun makeRequestSignUp(
        email: String,
        pw   : String,
        nick : String
    ): Flow<Result<SignUpResponse>>
}