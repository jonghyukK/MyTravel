package org.kjh.data.mapper

import org.kjh.data.model.*
import org.kjh.data.model.api.*
import org.kjh.domain.entity.*

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
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // LoginApiModel -> LoginEntity
    fun responseToLoginEntity(
        response: ApiResult<LoginApiModel>
    ): ApiResult<LoginEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // UserModel -> UserEntity
    fun responseToUserEntity(
        response: ApiResult<UserModel>
    ): ApiResult<UserEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // FollowModel -> FollowEntity
    fun responseToFollowEntity(
        response: ApiResult<FollowModel>
    ): ApiResult<FollowEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // PlaceModel -> PlaceEntity
    fun responseToPlace(
        response: ApiResult<PlaceModel>
    ): ApiResult<PlaceEntity> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapToDomain())
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // List<PlaceModel> -> List<PlaceEntity>
    fun responseToPlaceList(
        response: ApiResult<List<PlaceModel>>
    ): ApiResult<List<PlaceEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.map { it.mapToDomain() })
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // List<PlaceWithRankModel> -> List<PlaceWithRankEntity>
    fun responseToPlaceRankingList(
        response: ApiResult<List<PlaceWithRankModel>>
    ): ApiResult<List<PlaceWithRankEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.map { it.mapToDomain() })
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // List<BannerModel> -> List<BannerEntity>
    fun responseToBannerEntityList(
        response: ApiResult<List<BannerModel>>
    ): ApiResult<List<BannerEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.map { it.mapToDomain() })
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // List<BookmarkModel> -> List<BookmarkEntity>
    fun responseToBookMark(
        response: ApiResult<List<BookmarkModel>>
    ): ApiResult<List<BookmarkEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.map { it.mapToDomain() })
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // MapQueryApiModel -> List<MapQueryEntity>
    fun responseToMapQuery(
        response: ApiResult<MapQueryApiModel>
    ): ApiResult<List<MapQueryEntity>> {
        return when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.mapQueries.map { it.mapToDomain() })
            is ApiResult.Error   -> ApiResult.Error(response.throwable)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

//    fun responseToPostsEntity(
//        response: ApiResult<PostsApiModel>
//    ): ApiResult<PostEntity> {
//        return when (response) {
//            is ApiResult.Success -> ApiResult.Success(response.data)
//            is ApiResult.Error   -> ApiResult.Error(response.throwable)
//            is ApiResult.Loading -> ApiResult.Loading
//        }
//    }
}