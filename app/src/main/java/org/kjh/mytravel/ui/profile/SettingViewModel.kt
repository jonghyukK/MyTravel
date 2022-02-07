package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.LoginPreferencesRepository
import com.example.domain.usecase.GetLoginPreferenceUseCase
import com.example.domain.usecase.MakeLogOutWithDeleteProfileUseCase
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
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    private val makeLogOutWithDeleteProfileUseCase: MakeLogOutWithDeleteProfileUseCase
): ViewModel() {

    val loginInfoPreferencesFlow = loginPreferencesRepository.loginInfoPreferencesFlow

    fun logout() {
        viewModelScope.launch {
            makeLogOutWithDeleteProfileUseCase(getLoginPreferenceUseCase().email)
        }
    }
}