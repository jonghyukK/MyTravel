package org.kjh.mytravel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.orhanobut.logger.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: DataStoreManager
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

private val Context.dataStore by preferencesDataStore("settings")

@Singleton //You can ignore this annotation as return `datastore` from `preferencesDataStore` is singletone
class DataStoreManager @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val settingsDataStore = appContext.dataStore

    private val keyMyEmail = stringPreferencesKey("KEY_MY_EMAIL")

    suspend fun saveMyEmail(email: String) {
        settingsDataStore.edit { settings ->
            settings[keyMyEmail] = email
        }
    }

    val getMyEmail: Flow<String> = settingsDataStore.data.map { pref ->
        pref[keyMyEmail] ?: ""
    }
}