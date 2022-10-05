package org.kjh.mytravel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import org.kjh.data.api.ApiService
import org.kjh.data.api.KakaoApiService
import org.kjh.data.dao.UserDao
import org.kjh.data.datasource.*
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
    fun provideUserLocalDataSource(
        userDao: UserDao
    ): UserLocalDataSource = UserLocalDataSourceImpl(userDao)

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun provideDayLogRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DayLogRemoteDataSource = DayLogRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun providePlaceRemoteDataSource(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PlaceRemoteDataSource = PlaceRemoteDataSourceImpl(apiService, ioDispatcher)

    @Singleton
    @Provides
    fun provideMapRemoteDataSource(
        kakaoApiService: KakaoApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MapRemoteDataSource = MapRemoteDataSourceImpl(kakaoApiService, ioDispatcher)
}