package org.kjh.mytravel.ui.features.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetMyProfileUseCase
import org.kjh.domain.usecase.UpdateProfileUseCase
import javax.inject.Inject

/**
 * MyTravel
 * Class: ProfileEditViewModel
 * Created by jonghyukkang on 2022/01/25.
 *
 * Description:
 */

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getMyProfileUseCase : GetMyProfileUseCase,
    private val eventHandler        : EventHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState = _uiState.asStateFlow()

    private val _profileUpdateState: MutableStateFlow<UpdateProfileUiState> =
        MutableStateFlow(UpdateProfileUiState.Init)
    val profileUpdateState = _profileUpdateState.asStateFlow()

    init {
        makeInitUiState()
    }

    private fun makeInitUiState() {
        viewModelScope.launch {
            getMyProfileUseCase()
                .filterNotNull()
                .collect {
                    _uiState.value = ProfileEditUiState(
                        profileImgUrl = it.profileImg,
                        tempImgUrl  = it.profileImg,
                        nickName    = it.nickName,
                        introduce   = it.introduce
                    )
                }
        }
    }

    fun requestUpdateProfile() {
        viewModelScope.launch {
            val currentUiState = _uiState.value
            val isModifiedProfileUrl = currentUiState.profileImgUrl != currentUiState.tempImgUrl

            updateProfileUseCase(
                profileImg = if (isModifiedProfileUrl) currentUiState.tempImgUrl else null,
                nickName  = currentUiState.nickName,
                introduce = currentUiState.introduce
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _profileUpdateState.value = UpdateProfileUiState.Loading

                    is ApiResult.Success ->
                        _profileUpdateState.value = UpdateProfileUiState.Success

                    is ApiResult.Error -> {
                        val errorMsg = apiResult.throwable.message ?: "Unknown Error"
                        Logger.e(errorMsg)

                        eventHandler.emitEvent(
                            Event.ApiError("occur Error [Update Profile API]"))

                        _profileUpdateState.value =
                            UpdateProfileUiState.Failure(errorMsg)
                    }
                }
            }
        }
    }

    fun updateProfileImg(modifiedProfileUrl: String) {
        _uiState.update {
            it.copy(tempImgUrl = modifiedProfileUrl)
        }
    }

    val updateNickName = fun(modifiedNickName: String) {
        _uiState.update {
            it.copy(nickName = modifiedNickName)
        }
    }

    val updateIntroduce = fun(modifiedIntroduce: String) {
        _uiState.update {
            it.copy(introduce = modifiedIntroduce)
        }
    }
}

data class ProfileEditUiState(
    val profileImgUrl: String? = null,
    val tempImgUrl   : String? = null,
    val nickName     : String = "",
    val introduce    : String? = null
)

sealed class UpdateProfileUiState {
    object Init: UpdateProfileUiState()
    object Loading: UpdateProfileUiState()
    object Success: UpdateProfileUiState()
    data class Failure(val errorMsg: String): UpdateProfileUiState()

    fun isLoading() = this is Loading
}