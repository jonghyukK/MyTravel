package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.UserResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: UserRepository
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */

interface UserRepository {

    fun getUserInfo(
        email: String
    ): Flow<Result<UserResponse>>
}