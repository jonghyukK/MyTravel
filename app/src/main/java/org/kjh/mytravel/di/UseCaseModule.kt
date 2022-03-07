package org.kjh.mytravel.di

import com.example.domain2.repository.*
import com.example.domain2.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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

    @Provides
    fun provideGetLoginPreferenceUseCase(
        loginPreferencesRepository: LoginPreferencesRepository
    ): GetLoginPreferenceUseCase = GetLoginPreferenceUseCase(loginPreferencesRepository)

    @Provides
    fun provideGetPlaceRankingUseCase(
        placeRepository: PlaceRepository
    ): GetPlaceRankingUseCase = GetPlaceRankingUseCase(placeRepository)

    @Provides
    fun provideGetPlaceUseCase(
        placeRepository: PlaceRepository
    ): GetPlaceUseCase = GetPlaceUseCase(placeRepository)

    @Provides
    fun provideGetRecentPostsUseCase(
        postRepository: PostRepository
    ): GetRecentPostsUseCase = GetRecentPostsUseCase(postRepository)

    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase = GetUserUseCase(userRepository)

    @Provides
    fun provideMakeLoginRequestUseCase(
        loginRepository             : LoginRepository,
        saveLogInPreferenceUseCase  : SaveLogInPreferenceUseCase
    ): MakeLoginRequestUseCase = MakeLoginRequestUseCase(loginRepository, saveLogInPreferenceUseCase)

    @Provides
    fun provideMakeLogOutRequestUseCase(
        loginPreferencesRepository: LoginPreferencesRepository
    ): MakeLogOutRequestUseCase = MakeLogOutRequestUseCase(loginPreferencesRepository)

    @Provides
    fun provideMakeRequestFollowOrNotUseCase(
        userRepository: UserRepository
    ): MakeRequestFollowOrNotUseCase = MakeRequestFollowOrNotUseCase(userRepository)

    @Provides
    fun provideMakeSignUpRequestUseCase(
        repository: SignUpRepository,
        saveLogInPreferenceUseCase: SaveLogInPreferenceUseCase,
    ): MakeSignUpRequestUseCase = MakeSignUpRequestUseCase(repository, saveLogInPreferenceUseCase)

    @Provides
    fun provideSaveLogInPreferenceUseCase(
        loginPreferencesRepository: LoginPreferencesRepository
    ): SaveLogInPreferenceUseCase = SaveLogInPreferenceUseCase(loginPreferencesRepository)

    @Provides
    fun provideSearchMapUseCase(
        mapRepository: MapRepository
    ): SearchMapUseCase = SearchMapUseCase(mapRepository)

    @Provides
    fun provideUpdateBookMarkUseCase(
        userRepository: UserRepository,
        loginPreferenceUseCase: GetLoginPreferenceUseCase
    ): UpdateBookMarkUseCase = UpdateBookMarkUseCase(userRepository, loginPreferenceUseCase)

    @Provides
    fun provideUpdateProfileUseCase(
        userRepository: UserRepository
    ): UpdateProfileUseCase = UpdateProfileUseCase(userRepository)

    @Provides
    fun provideUploadPostUseCase(
        postRepository: PostRepository
    ): UploadPostUseCase = UploadPostUseCase(postRepository)

    @Provides
    fun provideGetHomeBannersUseCase(
        placeRepository: PlaceRepository
    ): GetHomeBannersUseCase = GetHomeBannersUseCase(placeRepository)
}