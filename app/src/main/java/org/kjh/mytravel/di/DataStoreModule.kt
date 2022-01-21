package org.kjh.mytravel.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * MyTravel
 * Class: DataStoreModule
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */

//
//@Module
//@InstallIn(SingletonComponent::class)
//object DataStoreModule {
//
//    @Singleton
//    @Provides
//    fun providePreferencesDataStore(
//        @ApplicationContext appContext: Context
//    ): DataStore<Preferences> =
//        PreferenceDataStoreFactory.create(
//            produceFile = {
//                appContext.preferencesDataStoreFile("Settings")
//            }
//        )
//}