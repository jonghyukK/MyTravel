package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.PlaceModel
import org.kjh.mytravel.data.model.PlaceResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: PlaceRepository
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
interface PlaceRepository {
    fun getPlaceDetail(placeName: String): Flow<Result<PlaceResponse>>
}