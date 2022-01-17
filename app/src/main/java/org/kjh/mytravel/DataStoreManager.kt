package org.kjh.mytravel

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * MyTravel
 * Class: DataStoreManager
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
class DataStoreManager(
    private val ctx: Context
    ) {

    private val Context.dataStore by preferencesDataStore(name = "dataStore")

    private val booleanKey = booleanPreferencesKey("loginState")

    val isLogin: Flow<Boolean> = ctx.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[booleanKey] ?: false
        }

    suspend fun setBoolean(bool: Boolean) {
        ctx.dataStore.edit { preferences ->
            preferences[booleanKey] = bool
        }
    }
}