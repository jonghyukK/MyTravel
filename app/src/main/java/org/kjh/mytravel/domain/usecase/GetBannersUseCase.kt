package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.BannerRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetBannersUseCase
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
class GetBannersUseCase @Inject constructor(
    private val bannerRepository: BannerRepository
){
    fun execute() = bannerRepository.getBannerItems()
}