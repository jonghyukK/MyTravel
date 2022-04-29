package org.kjh.mytravel.ui.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.UpdateProfileUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.common.ErrorMsg

/**
 * MyTravel
 * Class: ProfileEditViewModel
 * Created by jonghyukkang on 2022/01/25.
 *
 * Description:
 */

class ProfileEditViewModel @AssistedInject constructor(
    private val updateProfileUseCase     : UpdateProfileUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    @Assisted("initProfileImg") private val initProfileImg: String?,
    @Assisted("initNickName"  ) private val initNickName: String,
    @Assisted("initIntroduce" ) private val initIntroduce: String?,
): ViewModel() {

    @AssistedFactory
    interface ProfileEditAssistedFactory {
        fun create(
            @Assisted("initProfileImg") initProfileImg: String?,
            @Assisted("initNickName"  ) initNickName: String,
            @Assisted("initIntroduce" ) initIntroduce: String?
        ): ProfileEditViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: ProfileEditAssistedFactory,
            initProfileImg : String?,
            initNickName   : String,
            initIntroduce  : String?
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                assistedFactory.create(initProfileImg, initNickName, initIntroduce) as T
        }
    }

    private val _profileImg: MutableStateFlow<String?> = MutableStateFlow(initProfileImg)
    val profileImg = _profileImg.asStateFlow()

    private val _profileUpdateState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Init)
    val profileUpdateState = _profileUpdateState.asStateFlow()

    val inputNickName  = MutableStateFlow(initNickName)
    val inputIntroduce = MutableStateFlow(initIntroduce)


    // API - Update My Profile.
    fun makeUpdateUserInfo(filePath: String?) {
        val nickName  = inputNickName.value
        val introduce = inputIntroduce.value

        viewModelScope.launch {
            updateProfileUseCase(
                profileImg = filePath,
                email      = getLoginPreferenceUseCase().email,
                nickName   = nickName,
                introduce  = introduce
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _profileUpdateState.value = UiState.Loading

                    is ApiResult.Success ->
                        _profileUpdateState.value = UiState.Success(apiResult.data.mapToPresenter())

                    is ApiResult.Error -> {
                        _profileUpdateState.value = UiState.Error(ErrorMsg.ERROR_MY_PROFILE_UPDATE)
                    }
                }
            }
        }
    }

    fun updateProfileImg(uri: String) {
        _profileImg.value = uri
    }

    fun shownErrorToast() {
        _profileUpdateState.value = UiState.Init
    }
}