package org.kjh.domain.usecase

import androidx.paging.map
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.kjh.domain.entity.LatestDayLogEntity
import org.kjh.domain.repository.DayLogRepository

/**
 * MyTravel
 * Class: GetLatestDayLogUseCase
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */
class GetLatestDayLogUseCase(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val dayLogRepository   : DayLogRepository,
) {
    private suspend fun isBookmarked(placeName: String) =
        getMyProfileUseCase().firstOrNull()?.bookMarks?.any { it.placeName == placeName } ?: false

    private suspend fun isMyDayLog(email: String) =
        getMyProfileUseCase().firstOrNull()?.email == email

    operator fun invoke() =
        dayLogRepository.fetchLatestDayLogs()
            .map { pagingData ->
                pagingData.map { dayLogEntity ->
                    LatestDayLogEntity(
                        dayLogId     = dayLogEntity.dayLogId,
                        email        = dayLogEntity.email,
                        nickName     = dayLogEntity.nickName,
                        content      = dayLogEntity.content,
                        cityName     = dayLogEntity.cityName,
                        subCityName  = dayLogEntity.subCityName,
                        placeName    = dayLogEntity.placeName,
                        placeAddress = dayLogEntity.placeAddress,
                        profileImg   = dayLogEntity.profileImg,
                        createdDate  = dayLogEntity.createdDate,
                        imageUrl     = dayLogEntity.imageUrl,
                        isBookmarked = isBookmarked(dayLogEntity.placeName),
                        isMyDayLog   = isMyDayLog(dayLogEntity.email)
                    )
                }
            }
}