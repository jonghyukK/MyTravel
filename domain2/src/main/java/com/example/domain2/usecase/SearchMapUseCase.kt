package com.example.domain2.usecase

import com.example.domain2.repository.MapRepository

/**
 * MyTravel
 * Class: SearchMapUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class SearchMapUseCase(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke(query: String) = mapRepository.searchPlace(query)
}