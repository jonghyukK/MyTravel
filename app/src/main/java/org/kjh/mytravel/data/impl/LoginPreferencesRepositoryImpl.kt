package org.kjh.mytravel.data.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.IOException
import org.kjh.mytravel.domain.repository.LoginPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: LoginPreferencesRepositoryImpl
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */

data class LoginInfoPreferences(
    val email     : String,
    val isLoggedIn: Boolean
)

@Singleton
class LoginPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LoginPreferencesRepository {

    private object PreferencesKeys {
        val MY_EMAIL     = stringPreferencesKey("my_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    override val loginInfoPreferencesFlow: Flow<LoginInfoPreferences>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException){
                    Logger.e("Error reading Preferences")
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                mapLoginInfoPreferences(preferences)
            }


    override suspend fun updateLoginInfoPreferences(loginInfoPref: LoginInfoPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = loginInfoPref.isLoggedIn
            preferences[PreferencesKeys.MY_EMAIL]     = loginInfoPref.email
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapLoginInfoPreferences(dataStore.data.first().toPreferences())

    private fun mapLoginInfoPreferences(preferences: Preferences): LoginInfoPreferences {
        val myEmail    = preferences[PreferencesKeys.MY_EMAIL] ?: "empty"
        val isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false

        return LoginInfoPreferences(myEmail, isLoggedIn)
    }
}