package com.example.domain2.usecase

import com.example.domain2.repository.PlaceRepository

/**
 * MyTravel
 * Class: GetHomeBannersUseCase
 * Created by jonghyukkang on 2022/03/04.
 *
 * Description:
 */
class GetHomeBannersUseCase(
    private val placeRepository: PlaceRepository
){
    suspend operator fun invoke() = placeRepository.getPlaceBanners()
}