package org.kjh.mytravel.ui.profile.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.UpdateProfileUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: ProfileEditViewModel
 * Created by jonghyukkang on 2022/01/25.
 *
 * Description:
 */

data class ProfileEditUiState(
    val profileImg: String? = null,
    val userItem  : User?   = null,
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
)

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState: StateFlow<ProfileEditUiState> = _uiState

    val inputNickName  = MutableLiveData<String>()
    val inputIntroduce = MutableLiveData<String?>()

    fun initProfileInfo(profileImg: String?, nickName: String, introduce: String?) {
        viewModelScope.launch {
            _uiState.value = ProfileEditUiState(profileImg)
            inputNickName.value = nickName
            inputIntroduce.value = introduce
        }
    }

    fun updateProfileImg(uri: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    profileImg = uri
                )
            }
        }
    }

    fun makeUpdateUserInfo(filePath: String?) {
        val nickName  = inputNickName.value.toString()
        val introduce = inputIntroduce.value.toString()

        viewModelScope.launch {
            updateProfileUseCase(
                profileImg = filePath,
                email      = getLoginPreferenceUseCase().email,
                nickName   = nickName,
                introduce  = introduce
            ).collect { result ->
                when (result) {
                    is ApiResult.Loading -> _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    is ApiResult.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            userItem  = result.data.mapToPresenter()
                        )
                    }
                    is ApiResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = false
                            )
                        }
                        Logger.e(result.throwable.localizedMessage!!)
                    }
                }
            }
        }
    }
}