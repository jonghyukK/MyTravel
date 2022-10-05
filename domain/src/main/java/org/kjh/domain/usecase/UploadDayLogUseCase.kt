package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: UploadDayLogUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UploadDayLogUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        file: List<String>,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ) = userRepository.uploadDayLog(file, content, placeName, placeAddress, placeRoadAddress, x, y)
}