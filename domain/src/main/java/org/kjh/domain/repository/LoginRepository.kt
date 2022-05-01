package org.kjh.domain.repository

import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: LoginRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginRepository {
    suspend fun makeRequestLogin(email: String, pw: String): Flow<ApiResult<LoginEntity>>
}