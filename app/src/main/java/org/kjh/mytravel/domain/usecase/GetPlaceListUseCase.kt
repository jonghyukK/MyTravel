package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetPlaceListUseCase
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
class GetPlaceListUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {

    fun execute() = placeRepository.getRecentPlaceItems()
}