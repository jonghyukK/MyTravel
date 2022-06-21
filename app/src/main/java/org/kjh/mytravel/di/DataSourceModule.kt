package org.kjh.mytravel.di

import org.kjh.data.api.ApiService
import org.kjh.data.api.KakaoApiService
import org.kjh.data.datasource.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SignUpRemoteDataSource = SignUpDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LoginRemoteDataSource = LoginDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun providePostRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PostRemoteDataSource = PostRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun providePlaceRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PlaceRemoteDataSource = PlaceRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun provideBookmarkRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): BookmarkRemoteDataSource = BookmarkRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun provideMapRemoteDataSource(
        kakaoApiService: KakaoApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MapRemoteDataSource = MapRemoteDataSourceImpl(kakaoApiService, ioDispatcher)
}