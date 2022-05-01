package org.kjh.domain.usecase

import org.kjh.domain.repository.PlaceRepository

/**
 * MyTravel
 * Class: GetPlaceUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class GetPlaceUseCase(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(placeName: String) =
        placeRepository.getPlace(placeName)
}