package org.kjh.mytravel.di

import org.kjh.data.api.ApiService
import org.kjh.data.api.KakaoApiService
import org.kjh.data.datasource.*
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
    fun provideSignUpRemoteDataSource(
        apiService: ApiService
    ): SignUpRemoteDataSource = SignUpDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(
        apiService: ApiService
    ): LoginRemoteDataSource = LoginDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        apiService: ApiService
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun providePostRemoteDataSource(
        apiService: ApiService
    ): PostRemoteDataSource = PostRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun providePlaceRemoteDataSource(
        apiService: ApiService
    ): PlaceRemoteDataSource = PlaceRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideBookmarkRemoteDataSource(
        apiService: ApiService
    ): BookmarkRemoteDataSource = BookmarkRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideMapRemoteDataSource(
        kakaoApiService: KakaoApiService
    ): MapRemoteDataSource = MapRemoteDataSourceImpl(kakaoApiService)
}