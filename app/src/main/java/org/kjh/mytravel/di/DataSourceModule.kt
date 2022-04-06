package org.kjh.mytravel.di

import com.example.data.api.ApiService
import com.example.data.api.KakaoApiService
import com.example.data.datasource.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * MyTravel
 * Class: DataSourceModule
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideSignUpDataSource(
        apiService: ApiService
    ): SignUpRemoteDataSource {
        return SignUpDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(
        apiService: ApiService
    ): LoginRemoteDataSource {
        return LoginDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideUserDataSource(
        apiService: ApiService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMapDataSource(
        kakaoApiService: KakaoApiService
    ): MapRemoteDataSource {
        return MapRemoteDataSourceImpl(kakaoApiService)
    }

    @Singleton
    @Provides
    fun providePostDataSource(
        apiService: ApiService
    ): PostRemoteDataSource {
        return PostRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providePlaceDataSource(
        apiService: ApiService
    ): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideBookmarkDataSource(
        apiService: ApiService
    ): BookmarkRemoteDataSource {
        return BookmarkRemoteDataSourceImpl(apiService)
    }
}