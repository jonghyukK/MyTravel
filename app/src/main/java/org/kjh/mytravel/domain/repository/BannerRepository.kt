package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.BannerResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: BannerRepository
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
interface BannerRepository {
    fun getBannerItems(): Flow<Result<BannerResponse>>
}