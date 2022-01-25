package org.kjh.mytravel.data.impl

import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.UserResponse
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.UserRepository
import java.io.File
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

    override fun updateUserInfo(
        file        : String?,
        email       : String,
        nickName    : String?,
        introduce   : String?
    ): Flow<Result<UserResponse>> = flow {
        emit(Result.Loading())

        try {
            var filePath = file

            if (file != null && file.startsWith("/data")) {
                filePath = file.replace("${email}_", "")
            }

            val fileBody = filePath?.let {
                val tempFile = File(filePath)
                val requestFile = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
            } ?: MultipartBody.Part.createFormData("","")

            val result = apiService.makeUpdateUser(
                file        = fileBody,
                email       = email,
                nickName    = nickName,
                introduce   = introduce
            )

            emit(responseToUserResult(Result.Success(result)))
        } catch (e: Exception) {
            emit(responseToUserResult(Result.Error(e)))
        }
    }
}