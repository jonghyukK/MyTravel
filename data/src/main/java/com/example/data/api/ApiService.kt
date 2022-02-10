package com.example.data.api

import com.example.data.model.LoginResponse
import com.example.data.model.PlaceResponse
import com.example.data.model.SignUpResponse
import com.example.domain.entity.BookmarkResponse
import com.example.domain.entity.UpdateProfile
import com.example.domain.entity.UploadPostResponse
import com.example.domain.entity.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * MyTravel
 * Class: ApiService
 * Created by jonghyukkang on 2022/01/27.
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
        @Query("myEmail"    ) myEmail    : String,
        @Query("targetEmail") targetEmail: String? = null
    ): UserResponse

    @Multipart
    @PUT("user/update")
    suspend fun updateUser(
        @Part file: MultipartBody.Part,
        @Query("email"      ) email     : String,
        @Query("nickName"   ) nickName  : String,
        @Query("introduce"  ) introduce : String?
    ): UpdateProfile

    @Multipart
    @POST("post/upload")
    suspend fun makeRequestPostUpload(
        @Part file: List<MultipartBody.Part>,
        @Query("email"          ) email         : String,
        @Query("content"        ) content       : String? = null,
        @Query("placeName"      ) placeName     : String,
        @Query("placeAddress"   ) placeAddress  : String,
        @Query("placeRoadAddress") placeRoadAddress: String,
        @Query("x") x: String,
        @Query("y") y: String
    ): UploadPostResponse

    @GET("post")
    suspend fun getPostsByPlaceName(
        @Query("placeName") placeName: String
    ): PlaceResponse

    @PUT("user/follow")
    suspend fun requestFollowOrUnFollow(
        @Query("myEmail") myEmail: String,
        @Query("targetEmail") targetEmail: String
    ): UserResponse

    @PUT("bookmark")
    suspend fun updateBookmark(
        @Query("email") email: String,
        @Query("postId") postId: Int,
        @Query("placeName") placeName: String
    ): BookmarkResponse
}