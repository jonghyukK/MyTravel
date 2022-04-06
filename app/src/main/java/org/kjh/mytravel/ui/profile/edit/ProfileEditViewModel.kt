package org.kjh.mytravel.ui.profile.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.UpdateProfileUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.base.UiState
import javax.inject.Inject

/**
 * MyTravel
 * Class: ProfileEditViewModel
 * Created by jonghyukkang on 2022/01/25.
 *
 * Description:
 */

sealed class ProfileUpdateState {
    object Init : ProfileUpdateState()
    object Loading : ProfileUpdateState()
    data class Success(val userItem: User): ProfileUpdateState()
    data class Error(val error: String?): ProfileUpdateState()
}

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {
    private val _profileImg: MutableStateFlow<String?> = MutableStateFlow(null)
    val profileImg = _profileImg.asStateFlow()

    private val _profileUpdateState: MutableStateFlow<ProfileUpdateState> = MutableStateFlow(ProfileUpdateState.Init)
    val profileUpdateState = _profileUpdateState.asStateFlow()

    val inputNickName  = MutableLiveData<String>()
    val inputIntroduce = MutableLiveData<String?>()

    fun initProfileInfo(profileImg: String?, nickName: String, introduce: String?) {
        _profileImg.value    = profileImg
        inputNickName.value  = nickName
        inputIntroduce.value = introduce
    }

    fun updateProfileImg(uri: String) {
        _profileImg.value = uri
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
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _profileUpdateState.value = ProfileUpdateState.Loading

                    is ApiResult.Success ->
                        _profileUpdateState.value = ProfileUpdateState.Success(apiResult.data.mapToPresenter())

                    is ApiResult.Error -> {
                        _profileUpdateState.value = ProfileUpdateState.Error(apiResult.throwable.localizedMessage)
                    }
                }
            }
        }
    }

    fun shownErrorToast() {
        _profileUpdateState.value = ProfileUpdateState.Init
    }
}