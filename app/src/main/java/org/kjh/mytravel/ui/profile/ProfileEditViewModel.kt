package org.kjh.mytravel.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.UserRepository
import org.kjh.mytravel.domain.usecase.GetUserInfoUseCase
import java.io.File
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
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
)

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState: StateFlow<ProfileEditUiState> = _uiState

    val inputNickName  = MutableLiveData<String>()
    val inputIntroduce = MutableLiveData<String?>()

    fun initProfileInfo(profileImg: String?, nickName: String, introduce: String?) {
        viewModelScope.launch {
            _uiState.value = ProfileEditUiState(profileImg)
        }

        inputNickName.value = nickName
        inputIntroduce.value = introduce
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
            getUserInfoUseCase.updateUserProfile(
                filePath  = filePath,
                nickName  = nickName,
                introduce = introduce
            )
                .collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                        is Result.Success -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = result.data.result
                            )
                        }
                        is Result.Error -> {
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