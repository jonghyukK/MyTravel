package com.example.domain.usecase

import com.example.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetPlaceUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class GetPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(placeName: String) = placeRepository.getPlace(placeName)
}