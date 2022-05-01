package org.kjh.domain.usecase

import org.kjh.domain.repository.PlaceRepository

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