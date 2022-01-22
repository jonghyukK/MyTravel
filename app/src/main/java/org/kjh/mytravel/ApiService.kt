package org.kjh.mytravel

import okhttp3.MultipartBody
import org.kjh.mytravel.data.model.LoginResponse
import org.kjh.mytravel.data.model.PostUploadResponse
import org.kjh.mytravel.data.model.SignUpResponse
import org.kjh.mytravel.data.model.UserResponse
import org.kjh.mytravel.domain.Result
import retrofit2.http.*

/**
 * MyTravel
 * Class: ApiService
 * Created by mac on 2022/01/17.
 *
 * Description:
 */
interface ApiService {

    @POST("user")
    suspend fun createUser(
        @Query("email"   ) email    : String,
        @Query("pw"      ) pw       : String,
        @Query("nickName") nickName : String
    ): SignUpResponse

    @GET("user/login")
    suspend fun login(
        @Query("email") email    : String,
        @Query("pw"   ) pw       : String
    ): LoginResponse

    @GET("user")
    suspend fun getUser(
        @Query("email") email: String
    ): UserResponse

    @Multipart
    @POST("user/upload")
    suspend fun makeRequestPostUpload(
        @Part file: List<MultipartBody.Part>,
        @Query("email"          ) email         : String,
        @Query("content"        ) content       : String? = null,
        @Query("cityName"       ) cityName      : String,
        @Query("placeName"      ) placeName     : String,
        @Query("placeAddress"   ) placeAddress  : String,
    ): PostUploadResponse
}