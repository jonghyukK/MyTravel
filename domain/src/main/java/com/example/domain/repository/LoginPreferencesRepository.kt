package com.example.domain.repository

import com.example.domain.entity.LoginInfoPreferences
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: LoginPreferencesRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginPreferencesRepository {

    val loginInfoPreferencesFlow : Flow<LoginInfoPreferences>

    suspend fun fetchInitialPreferences(): LoginInfoPreferences

    suspend fun updateLoginInfoPreferences(loginInfoPref: LoginInfoPreferences)

    suspend fun makeRequestLogOut()
}