package org.kjh.mytravel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kjh.data.EventHandler
import org.kjh.data.datasource.*
import org.kjh.data.repository.DayLogRepositoryImpl
import org.kjh.data.repository.MapRepositoryImpl
import org.kjh.data.repository.PlaceRepositoryImpl
import org.kjh.data.repository.UserRepositoryImpl
import org.kjh.domain.repository.DayLogRepository
import org.kjh.domain.repository.MapRepository
import org.kjh.domain.repository.PlaceRepository
import org.kjh.domain.repository.UserRepository
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
    fun provideUserRepository(
        eventHandler: EventHandler,
        localDataSource: UserLocalDataSource,
        remoteDataSource: UserRemoteDataSource
    ): UserRepository = UserRepositoryImpl(eventHandler, localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideDayLogRepository(
        dataSource: DayLogRemoteDataSource
    ): DayLogRepository = DayLogRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideMapRepository(
        dataSource: MapRemoteDataSource
    ): MapRepository = MapRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun providePlaceRepository(
        dataSource: PlaceRemoteDataSource
    ): PlaceRepository = PlaceRepositoryImpl(dataSource)
}