package org.kjh.mytravel.data.mapper

import org.kjh.mytravel.data.model.*
import org.kjh.mytravel.domain.Result
/**
 * MyTravel
 * Class: ResponseMapper
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
object ResponseMapper {

    fun responseToSignUpResult(
        response: Result<SignUpResponse>
    ): Result<SignUpResponse>{
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }

    fun responseToLoginResult(
        response: Result<LoginResponse>
    ): Result<LoginResponse> {
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }

    fun responseToBannerResult(
        response: Result<BannerResponse>
    ): Result<BannerResponse> {
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }

    fun responseToRankingResult(
        response: Result<RankingModel>
    ): Result<RankingModel> {
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }

    fun responseToEventResult(
        response: Result<EventModel>
    ): Result<EventModel> {
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }

    fun responseToPlaceResult(
        response: Result<PlaceModel>
    ): Result<PlaceModel> {
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }

    fun responseToUploadResult(
        response: Result<PostUploadResponse>
    ): Result<PostUploadResponse> {
        return when (response) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.throwable)
            is Result.Loading -> Result.Loading()
        }
    }
}