package org.kjh.domain.usecase

import org.kjh.domain.repository.PlaceRepository

/**
 * MyTravel
 * Class: GetPlaceRankingUseCase
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
class GetPlaceRankingUseCase(private val placeRepository: PlaceRepository) {
    suspend operator fun invoke() = placeRepository.fetchPlaceRankings()
}