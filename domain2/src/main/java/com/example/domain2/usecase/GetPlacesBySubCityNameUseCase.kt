package com.example.domain2.usecase

import com.example.domain2.repository.PlaceRepository

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
        placeRepository.getPlacesBySubCityName(subCityName)
}