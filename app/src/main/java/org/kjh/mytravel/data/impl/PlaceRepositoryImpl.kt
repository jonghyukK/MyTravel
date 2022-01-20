package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.PlaceModel
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.PlaceRepository
import org.kjh.mytravel.ui.uistate.tempPlaceItemList
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: PlaceRepositoryImpl
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
@Singleton
class PlaceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToPlaceResult: (Result<PlaceModel>) -> Result<PlaceModel>
): PlaceRepository {
    override fun getRecentPlaceItems(): Flow<Result<PlaceModel>> = flow{
        val response = PlaceModel(
            placeList = tempPlaceItemList
        )

        val str = Result.Success(response)

        emit(responseToPlaceResult(str))
    }
}