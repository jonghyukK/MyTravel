package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.RankingRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetRankingUseCase
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
class GetRankingUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
){
    fun execute() = rankingRepository.getRankingItems()
}