package org.kjh.mytravel.ui.profile.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.repository.LoginPreferencesRepository
import com.example.domain2.usecase.MakeLogOutRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val loginPreferencesRepository: LoginPreferencesRepository,
    private val makeLogOutRequestUseCase: MakeLogOutRequestUseCase
): ViewModel() {

    val loginInfoPreferencesFlow = loginPreferencesRepository.loginInfoPreferencesFlow

    fun logout() {
        viewModelScope.launch {
            makeLogOutRequestUseCase()
        }
    }
}