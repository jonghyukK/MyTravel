package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.kjh.mytravel.domain.repository.LoginPreferencesRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: SettingViewModel
 * Created by jonghyukkang on 2022/01/25.
 *
 * Description:
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val loginPreferencesRepository: LoginPreferencesRepository
): ViewModel() {

    val loginInfoPreferencesFlow = loginPreferencesRepository.loginInfoPreferencesFlow

    fun logout() {
        viewModelScope.launch {
            loginPreferencesRepository.makeRequestLogOut()
        }
    }
}