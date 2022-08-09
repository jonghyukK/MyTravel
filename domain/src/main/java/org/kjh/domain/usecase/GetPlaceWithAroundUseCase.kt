package org.kjh.domain.usecase

import org.kjh.domain.repository.PlaceRepository

/**
 * MyTravel
 * Class: GetPlaceWithAroundUseCase
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */
class GetPlaceWithAroundUseCase(
    private val placeRepository: PlaceRepository
) {
    operator fun invoke(placeName: String) =
        placeRepository.fetchPlaceByPlaceNameWithAround(placeName)
}