package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.KakaoSearchResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: MapRepository
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
interface MapRepository {

    fun searchPlace(
        query: String
    ): Flow<Result<KakaoSearchResponse>>
}