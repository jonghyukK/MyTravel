package com.example.data.mapper

import com.example.data.model.*
import com.example.data.model.api.*
import com.example.domain2.entity.*

/**
 * MyTravel
 * Class: ResponseMapper
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
object ResponseMapper {

    // SignUpAPiModel -> SignUpEntity
    fun responseToSignUpEntity(
        response: ApiResult<SignUpApiModel>
    ): ApiResult<SignUpEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // LoginApiModel -> LoginEntity
    fun responseToLoginEntity(
        response: ApiResult<LoginApiModel>
    ): ApiResult<LoginEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // UserApiModel -> UserEntity
    fun responseToUserEntity(
        response: ApiResult<UserApiModel>
    ): ApiResult<UserEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // PlaceApiModel -> PlaceEntity
    fun responseToPlace(
        response: ApiResult<PlaceApiModel>
    ): ApiResult<PlaceEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // PlacesApiModel -> List<PlaceEntity>
    fun responseToPlaceList(
        response: ApiResult<PlacesApiModel>
    ): ApiResult<List<PlaceEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(
                response.data.data.map { it.mapToDomain() }
            )
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // PlaceRankingApiModel -> List<PlaceWithRankEntity>
    fun responseToPlaceRankingList(
        response: ApiResult<PlaceRankingApiModel>
    ): ApiResult<List<PlaceWithRankEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(
                response.data.data.map { it.mapToDomain() }
            )
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // BookmarksApiModel -> List<BookmarkEntity>
    fun responseToBookMark(
        response: ApiResult<BookmarksApiModel>
    ): ApiResult<List<BookmarkEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(
                response.data.data.map { it.mapToDomain() }
            )
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

    // MapQueryApiModel -> List<MapQueryEntity>
    fun responseToMapQuery(
        response: ApiResult<MapQueryApiModel>
    ): ApiResult<List<MapQueryEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(
                response.data.mapQueries.map { it.mapToDomain() }
            )
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }

//    fun responseToPostsEntity(
//        response: ApiResult<PostsApiModel>
//    ): ApiResult<PostEntity> {
//        return when (response) {
//            is ApiResult.Success -> ApiResult.Success(response.data)
//            is ApiResult.Error   -> ApiResult.Error(response.throwable)
//            is ApiResult.Loading -> ApiResult.Loading()
//        }
//    }










}