package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.RankingModel
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.RankingRepository
import org.kjh.mytravel.ui.uistate.tempRankingItems
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: RankingRepositoryImpl
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
@Singleton
class RankingRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToRankingResult: (Result<RankingModel>) -> Result<RankingModel>
): RankingRepository {

    override fun getRankingItems(): Flow<Result<RankingModel>> = flow {
        val response = RankingModel(
            rankingList = tempRankingItems
        )

        val str = Result.Success(response)

        emit(responseToRankingResult(str))
    }
}