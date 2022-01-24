package org.kjh.mytravel.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.KakaoApiService
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

    @Singleton
    @Provides
    fun providePostRepository(
        apiService: ApiService
    ): PostRepository {
        return PostRepositoryImpl(apiService, ResponseMapper::responseToUploadResult)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService
    ): UserRepository {
        return UserRepositoryImpl(apiService, ResponseMapper::responseToUserResult)
    }

    @Singleton
    @Provides
    fun provideLoginPreferencesRepository(
        dataStore: DataStore<Preferences>
    ): LoginPreferencesRepository {
        return LoginPreferencesRepositoryImpl(dataStore)
    }

    @Singleton
    @Provides
    fun provideMapRepository(
        kakaoApiService: KakaoApiService
    ): MapRepository {
        return MapRepositoryImpl(kakaoApiService, ResponseMapper::responseToSearchResult)
    }
}