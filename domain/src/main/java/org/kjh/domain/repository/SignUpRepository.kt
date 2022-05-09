package org.kjh.domain.repository

import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: SignUpRepository
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
interface SignUpRepository {

    suspend fun requestSignUp(
        email: String,
        pw   : String,
        nick : String
    ): Flow<ApiResult<SignUpEntity>>
}