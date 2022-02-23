package com.example.domain.usecase

import com.example.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetPlaceRankingUseCase
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
class GetPlaceRankingUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
){
    suspend operator fun invoke() = placeRepository.getPlaceRanking()
}