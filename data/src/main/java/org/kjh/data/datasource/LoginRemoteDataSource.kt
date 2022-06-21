package org.kjh.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
import org.kjh.data.model.LoginModel
import org.kjh.data.model.base.BaseApiModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: LoginDataStore
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginRemoteDataSource {

    suspend fun requestLogin(
        email: String,
        pw   : String
    ): BaseApiModel<LoginModel>

}

class LoginDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): LoginRemoteDataSource {

    override suspend fun requestLogin(email: String, pw: String)
    = withContext(ioDispatcher) {
        apiService.requestLogin(email, pw)
    }
}