package com.example.data.mapper

import com.example.data.model.*
import com.example.domain.entity.*

/**
 * MyTravel
 * Class: ResponseMapper
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
object ResponseMapper {

    // SignUpResponse -> SignUp
    fun responseToSignUpEntity(
        response: ApiResult<SignUpResponse>
    ): ApiResult<SignUp> {
        return when (response) {
            is ApiResult.Success -> {
                ApiResult.Success(SignUp(
                    isRegistered = response.data.isRegistered,
                    errorMsg     = response.data.errorMsg))
            }
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // LoginResponse -> Login
    fun responseToLoginEntity(
        response: ApiResult<LoginResponse>
    ): ApiResult<Login> {
        return when (response) {
            is ApiResult.Success -> {
                ApiResult.Success(Login(
                    isLoggedIn = response.data.isLoggedIn,
                    errorMsg   = response.data.errorMsg
                ))
            }
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    fun responseToUser(
        response: ApiResult<UserResponse>
    ): ApiResult<UserResponse> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data)
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    fun responseToUpdateProfile(
        response: ApiResult<UpdateProfile>
    ): ApiResult<UpdateProfile> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data)
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    fun responseToUploadPost(
        response: ApiResult<UploadPostResponse>
    ): ApiResult<UploadPostResponse> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data)
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    fun responseToMapSearch(
        response: ApiResult<MapSearchResponse>
    ): ApiResult<List<MapSearch>> {
        return when (response) {
            is ApiResult.Success -> {
                ApiResult.Success(response.data.placeList.map {
                    MapSearch(
                        it.placeId,
                        it.placeName,
                        it.placeAddress,
                        it.placeRoadAddress,
                        it.x,
                        it.y
                    )
                })
            }
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    fun responseToPlace(
        response: ApiResult<PlaceResponse>
    ): ApiResult<Place> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.data)
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    fun responseToBookMark(
        response: ApiResult<BookmarkResponse>
    ): ApiResult<BookmarkResponse> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data)
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }
}