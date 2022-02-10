package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import com.example.domain.entity.User
import com.example.domain.usecase.GetLoginPreferenceUseCase
import com.example.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * MyTravel
 * Class: UserProfileViewModel
 * Created by jonghyukkang on 2022/02/10.
 *
 * Description:
 */

data class MyProfileUiState(
    val userItem  : User? = null,
    val isLoggedIn: Boolean = true
)

data class OtherUserUiState(
    val userItem: User? = null
)

@HiltViewModel
class UserProfileViewModel @Inject constructor(
): ViewModel() {

    private val _myProfileUiState = MutableStateFlow(MyProfileUiState())
    val myProfileUiState: StateFlow<MyProfileUiState> = _myProfileUiState

    private val _otherUserUiState = MutableStateFlow(OtherUserUiState())
    val otherUserUiState: StateFlow<OtherUserUiState> = _otherUserUiState


}