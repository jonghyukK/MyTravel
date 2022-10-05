package org.kjh.mytravel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kjh.domain.repository.DayLogRepository
import org.kjh.domain.repository.MapRepository
import org.kjh.domain.repository.PlaceRepository
import org.kjh.domain.repository.UserRepository
import org.kjh.domain.usecase.*
import javax.inject.Singleton

/**
 * MyTravel
 * Class: UseCaseModule
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPlaceRankingUseCase(
        placeRepository: PlaceRepository
    ): GetPlaceRankingUseCase = GetPlaceRankingUseCase(placeRepository)

    @Singleton
    @Provides
    fun provideGetPlaceUseCase(
        placeRepository: PlaceRepository
    ): GetPlaceUseCase = GetPlaceUseCase(placeRepository)

    @Singleton
    @Provides
    fun provideGetPlaceWithAroundUseCase(
        placeRepository: PlaceRepository
    ): GetPlaceWithAroundUseCase = GetPlaceWithAroundUseCase(placeRepository)

    @Singleton
    @Provides
    fun provideGetLatestDayLogUseCase(
        getMyProfileUseCase: GetMyProfileUseCase,
        dayLogRepository     : DayLogRepository
    ): GetLatestDayLogUseCase = GetLatestDayLogUseCase(getMyProfileUseCase, dayLogRepository)

    @Singleton
    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase = GetUserUseCase(userRepository)

    @Singleton
    @Provides
    fun provideMakeLoginRequestUseCase(
        userRepository: UserRepository
    ): MakeLoginRequestUseCase = MakeLoginRequestUseCase(userRepository)

    @Singleton
    @Provides
    fun provideMakeRequestFollowOrNotUseCase(
        userRepository: UserRepository
    ): MakeRequestFollowOrNotUseCase = MakeRequestFollowOrNotUseCase(userRepository)

    @Singleton
    @Provides
    fun provideMakeSignUpRequestUseCase(
        userRepository: UserRepository
    ): MakeSignUpRequestUseCase = MakeSignUpRequestUseCase(userRepository)

    @Singleton
    @Provides
    fun provideSearchMapUseCase(
        mapRepository: MapRepository
    ): SearchMapUseCase = SearchMapUseCase(mapRepository)

    @Singleton
    @Provides
    fun provideUpdateBookMarkUseCase(
        userRepository: UserRepository
    ): UpdateBookmarkUseCase = UpdateBookmarkUseCase(userRepository)

    @Singleton
    @Provides
    fun provideUpdateProfileUseCase(
        userRepository: UserRepository
    ): UpdateProfileUseCase = UpdateProfileUseCase(userRepository)

    @Singleton
    @Provides
    fun provideUploadDayLogUseCase(
        userRepository: UserRepository
    ): UploadDayLogUseCase = UploadDayLogUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetHomeBannersUseCase(
        placeRepository: PlaceRepository
    ): GetHomeBannersUseCase = GetHomeBannersUseCase(placeRepository)

    @Singleton
    @Provides
    fun provideGetPlacesBySubCityNameUseCase(
        placeRepository: PlaceRepository
    ): GetPlacesBySubCityNameUseCase = GetPlacesBySubCityNameUseCase(placeRepository)

    @Singleton
    @Provides
    fun provideDeleteDayLogUseCase(
        userRepository: UserRepository
    ): DeleteDayLogUseCase = DeleteDayLogUseCase(userRepository)

    @Singleton
    @Provides
    fun provideDeleteMyUserUseCase(
        userRepository: UserRepository
    ): DeleteMyProfileUseCase = DeleteMyProfileUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetMyProfileUseCase(
        userRepository: UserRepository
    ): GetMyProfileUseCase = GetMyProfileUseCase(userRepository)
}