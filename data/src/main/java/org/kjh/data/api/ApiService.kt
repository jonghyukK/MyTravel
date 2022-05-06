package org.kjh.data.api

import okhttp3.MultipartBody
import org.kjh.data.model.*
import org.kjh.data.model.api.LoginApiModel
import org.kjh.data.model.api.SignUpApiModel
import org.kjh.data.model.base.BaseApiModel
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
    suspend fun fetchUser(
        @Query("myEmail"    ) myEmail    : String,
        @Query("targetEmail") targetEmail: String? = null
    ): BaseApiModel<UserModel>

    @Multipart
    @PUT("user/update")
    suspend fun updateMyProfile(
        @Part file: MultipartBody.Part,
        @Query("email"      ) email     : String,
        @Query("nickName"   ) nickName  : String,
        @Query("introduce"  ) introduce : String?
    ): BaseApiModel<UserModel>

    @PUT("user/follow")
    suspend fun updateFollowState(
        @Query("myEmail") myEmail: String,
        @Query("targetEmail") targetEmail: String
    ): BaseApiModel<FollowModel>


    /***********************************************
     *  about Post.
     ***********************************************/
    @Multipart
    @POST("user/upload")
    suspend fun uploadPost(
        @Part file: List<MultipartBody.Part>,
        @Query("email"          ) email         : String,
        @Query("content"        ) content       : String? = null,
        @Query("placeName"      ) placeName     : String,
        @Query("placeAddress"   ) placeAddress  : String,
        @Query("placeRoadAddress") placeRoadAddress: String,
        @Query("x") x: String,
        @Query("y") y: String
    ): BaseApiModel<UserModel>

    @GET("post/recent")
    suspend fun fetchLatestPosts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseApiModel<List<PostModel>>


    /***********************************************
     *  about Place.
     ***********************************************/
    @GET("place")
    suspend fun fetchPlaceDetailByPlaceName(
        @Query("placeName") placeName: String
    ): BaseApiModel<PlaceModel>

    @GET("place/ranking")
    suspend fun fetchPlaceRankings()
    : BaseApiModel<List<PlaceWithRankModel>>

    @GET("banners")
    suspend fun fetchPlaceBanners()
    : BaseApiModel<List<BannerModel>>

    @GET("place/subCityName")
    suspend fun fetchPlacesBySubCityName(
        @Query("subCityName") subCityName: String
    ): BaseApiModel<List<PlaceModel>>


    /***********************************************
     *  about Bookmark.
     ***********************************************/
    @PUT("bookmarks")
    suspend fun updateMyBookmarks(
        @Query("myEmail") myEmail    : String,
        @Query("postId") postId      : Int,
        @Query("placeName") placeName: String
    ): BaseApiModel<List<BookmarkModel>>

    @GET("bookmarks")
    suspend fun fetchMyBookmarks(
        @Query("myEmail") myEmail: String
    ): BaseApiModel<List<BookmarkModel>>
}