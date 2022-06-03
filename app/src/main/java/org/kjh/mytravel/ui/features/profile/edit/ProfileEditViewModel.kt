package org.kjh.mytravel.ui.features.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.UpdateProfileUseCase
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler
import org.kjh.mytravel.ui.common.UiState

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
    private val globalErrorHandler       : GlobalErrorHandler,
    @Assisted("initProfileImg") private val initProfileImg: String?,
    @Assisted("initNickName"  ) private val initNickName  : String,
    @Assisted("initIntroduce" ) private val initIntroduce : String?,
): ViewModel() {

    val inputNickName  = MutableStateFlow(initNickName)
    val inputIntroduce = MutableStateFlow(initIntroduce)

    private val _profileImg: MutableStateFlow<String?> = MutableStateFlow(initProfileImg)
    val profileImg = _profileImg.asStateFlow()

    private val _profileUpdateState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Init)
    val profileUpdateState = _profileUpdateState.asStateFlow()

    private val _isProfileImgModified = MutableStateFlow(false)

    fun requestUpdateProfile() {
        viewModelScope.launch {
            updateProfileUseCase(
                profileImg = if (_isProfileImgModified.value) _profileImg.value else null,
                email      = getLoginPreferenceUseCase().email,
                nickName   = inputNickName.value,
                introduce  = inputIntroduce.value
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> _profileUpdateState.value = UiState.Loading

                    is ApiResult.Success -> {
                        val result = apiResult.data.mapToPresenter()
                        _profileUpdateState.value = UiState.Success(result)
                    }

                    is ApiResult.Error -> {
                        Logger.e(apiResult.throwable.localizedMessage!!)

                        val errorMsg = "occur Error [Update Profile API]"
                        globalErrorHandler.sendError(errorMsg)
                        _profileUpdateState.value = UiState.Error(Throwable(errorMsg))
                    }
                }
            }
        }
    }

    fun updateProfileImg(uri: String) {
        _profileImg.value = uri
        _isProfileImgModified.value = true
    }

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
}