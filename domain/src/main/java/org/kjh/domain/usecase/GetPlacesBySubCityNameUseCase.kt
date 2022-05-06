package org.kjh.domain.usecase

import org.kjh.domain.repository.PlaceRepository

/**
 * MyTravel
 * Class: GetPlacesBySubCityNameUseCase
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */
class GetPlacesBySubCityNameUseCase(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(subCityName: String) =
        placeRepository.fetchPlacesBySubCityName(subCityName)
}