package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.RankingModel
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: RankingRepository
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
interface RankingRepository {
    fun getRankingItems(): Flow<Result<RankingModel>>
}