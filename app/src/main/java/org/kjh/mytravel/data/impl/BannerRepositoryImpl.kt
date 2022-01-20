package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.R
import org.kjh.mytravel.data.model.BannerResponse
import org.kjh.mytravel.domain.repository.BannerRepository
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.ui.uistate.tempBannerItems
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: BannerRepositoryImpl
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

@Singleton
class BannerRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToBannerResult: (Result<BannerResponse>) -> Result<BannerResponse>
): BannerRepository {

    override fun getBannerItems(): Flow<Result<BannerResponse>> = flow {
        val response = BannerResponse(
            bannerList = tempBannerItems
        )

        val str = Result.Success(response)
        emit(responseToBannerResult(str))
    }
}