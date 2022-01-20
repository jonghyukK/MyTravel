package org.kjh.mytravel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.impl.*
import org.kjh.mytravel.data.mapper.ResponseMapper
import org.kjh.mytravel.domain.repository.*
import javax.inject.Singleton

/**
 * MyTravel
 * Class: RepositoryModule
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSignUpRepository(
        apiService: ApiService
    ): SignUpRepository {
        return SignUpRepositoryImpl(apiService, ResponseMapper::responseToSignUpResult)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(
        apiService: ApiService
    ): LoginRepository {
        return LoginRepositoryImpl(apiService, ResponseMapper::responseToLoginResult)
    }

    @Singleton
    @Provides
    fun provideBannerRepository(
        apiService: ApiService
    ): BannerRepository {
        return BannerRepositoryImpl(apiService, ResponseMapper::responseToBannerResult)
    }

    @Singleton
    @Provides
    fun provideRankingRepository(
        apiService: ApiService
    ): RankingRepository {
        return RankingRepositoryImpl(apiService, ResponseMapper::responseToRankingResult)
    }

    @Singleton
    @Provides
    fun provideEventRepository(
        apiService: ApiService
    ): EventRepository {
        return EventRepositoryImpl(apiService, ResponseMapper::responseToEventResult)
    }

    @Singleton
    @Provides
    fun providePlaceRepository(
        apiService: ApiService
    ): PlaceRepository {
        return PlaceRepositoryImpl(apiService, ResponseMapper::responseToPlaceResult)
    }
}