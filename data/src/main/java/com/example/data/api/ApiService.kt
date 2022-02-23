package com.example.data.api

import com.example.data.model.LoginResponse
import com.example.data.model.PlaceResponse
import com.example.data.model.SignUpResponse
import com.example.domain.entity.*
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
    @POST("user/upload")
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

    @PUT("user/follow")
    suspend fun requestFollowOrUnFollow(
        @Query("myEmail") myEmail: String,
        @Query("targetEmail") targetEmail: String
    ): UserResponse

    @PUT("user/bookmark")
    suspend fun updateBookmark(
        @Query("email") email: String,
        @Query("postId") postId: Int
    ): BookmarkResponse

    @GET("place")
    suspend fun getPlaceByPlaceName(
        @Query("placeName") placeName: String
    ): PlaceResponse

    @GET("place/ranking")
    suspend fun getPlaceRanking(): PlaceRankingResponse

    @GET("post/recent")
    suspend fun getRecentPosts(
        @Query("myEmail") myEmail: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): RecentPostsResponse
}