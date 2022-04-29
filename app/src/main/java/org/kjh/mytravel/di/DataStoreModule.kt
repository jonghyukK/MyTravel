package org.kjh.mytravel.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * MyTravel
 * Class: DataStoreModule
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val LOGIN_INFO_PREFERENCES_NAME = "login_info_preferences"
    private val Context.dataStore by preferencesDataStore(LOGIN_INFO_PREFERENCES_NAME)

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Singleton
    @Provides
    fun provideScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}