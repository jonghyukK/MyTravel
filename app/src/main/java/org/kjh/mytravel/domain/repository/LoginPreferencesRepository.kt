package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.impl.LoginInfoPreferences

/**
 * MyTravel
 * Class: LoginPreferencesRepository
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */
interface LoginPreferencesRepository {

    val loginInfoPreferencesFlow : Flow<LoginInfoPreferences>

    suspend fun fetchInitialPreferences(): LoginInfoPreferences

    suspend fun updateLoginInfoPreferences(loginInfoPref: LoginInfoPreferences)
}