package com.example.domain2.usecase

import com.example.domain2.repository.PlaceRepository

/**
 * MyTravel
 * Class: GetPlaceRankingUseCase
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
class GetPlaceRankingUseCase(
    private val placeRepository: PlaceRepository
){
    suspend operator fun invoke() = placeRepository.getPlaceRanking()
}