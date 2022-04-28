package com.example.data.api

import com.example.data.model.api.*
import com.example.domain2.entity.*
import kotlinx.coroutines.flow.Flow
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
    ): SignUpApiModel

    @GET("user/login")
    suspend fun login(
        @Query("email") email    : String,
        @Query("pw"   ) pw       : String
    ): LoginApiModel

    @GET("user")
    suspend fun getUser(
        @Query("myEmail"    ) myEmail    : String,
        @Query("targetEmail") targetEmail: String? = null
    ): UserApiModel

    @Multipart
    @PUT("user/update")
    suspend fun updateUser(
        @Part file: MultipartBody.Part,
        @Query("email"      ) email     : String,
        @Query("nickName"   ) nickName  : String,
        @Query("introduce"  ) introduce : String?
    ): UserApiModel

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
    ): UserApiModel

    @PUT("user/follow")
    suspend fun requestFollowOrUnFollow(
        @Query("myEmail") myEmail: String,
        @Query("targetEmail") targetEmail: String
    ): FollowApiModel

    @GET("post/recent")
    suspend fun getRecentPosts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PostsApiModel

    @GET("place")
    suspend fun getPlaceByPlaceName(
        @Query("placeName") placeName: String
    ): PlaceApiModel

    @GET("place/ranking")
    suspend fun getPlaceRanking(): PlaceRankingApiModel

    @GET("banners")
    suspend fun getHomeBanners(): BannersApiModel

    @GET("place/subCityName")
    suspend fun getPlacesBySubCityName(
        @Query("subCityName") subCityName: String
    ): PlacesApiModel

    @PUT("bookmarks")
    suspend fun updateBookmark(
        @Query("myEmail") myEmail    : String,
        @Query("postId") postId      : Int,
        @Query("placeName") placeName: String
    ): BookmarksApiModel

    @GET("bookmarks")
    suspend fun getBookmarks(
        @Query("myEmail") myEmail    : String
    ): BookmarksApiModel
}