package org.kjh.domain.usecase

import org.kjh.domain.repository.MapRepository

/**
 * MyTravel
 * Class: SearchMapUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class SearchMapUseCase(private val mapRepository: MapRepository) {
    suspend operator fun invoke(query: String) = mapRepository.searchPlace(query)
}