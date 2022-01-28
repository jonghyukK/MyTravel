package com.example.domain.usecase

import com.example.domain.repository.MapRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: SearchMapUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class SearchMapUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke(query: String) = mapRepository.searchPlace(query)
}