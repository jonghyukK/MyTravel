package com.example.domain2.usecase

import com.example.domain2.repository.PlaceRepository

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