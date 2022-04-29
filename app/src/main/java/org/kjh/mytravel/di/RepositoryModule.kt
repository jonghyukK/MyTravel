package org.kjh.mytravel.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.datasource.*
import com.example.data.repository.*
import com.example.domain2.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
        dataSource: SignUpRemoteDataSource
    ): SignUpRepository = SignUpRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideLoginRepository(
        dataSource: LoginRemoteDataSource
    ): LoginRepository = LoginRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideUserRepository(
        dataSource: UserRemoteDataSource
    ): UserRepository = UserRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun providePostRepository(
        dataSource: PostRemoteDataSource
    ): PostRepository = PostRepositoryImpl(dataSource)

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

    @Singleton
    @Provides
    fun provideBookmarkRepository(
        dataSource: BookmarkRemoteDataSource
    ): BookmarkRepository = BookmarkRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideLoginPreferencesRepository(
        dataStore: DataStore<Preferences>
    ): LoginPreferencesRepository = LoginPreferencesRepositoryImpl(dataStore)
}