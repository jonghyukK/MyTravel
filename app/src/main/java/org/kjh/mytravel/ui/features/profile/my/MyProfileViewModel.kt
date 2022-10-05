package org.kjh.mytravel.ui.features.profile.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.DeleteDayLogUseCase
import org.kjh.domain.usecase.DeleteMyProfileUseCase
import org.kjh.domain.usecase.GetMyProfileUseCase
import org.kjh.domain.usecase.UpdateBookmarkUseCase
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: MyProfileViewModel
 * Created by jonghyukkang on 2022/04/11.
 *
 * Description:
 */

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase   : GetMyProfileUseCase,
    private val updateBookmarkUseCase : UpdateBookmarkUseCase,
    private val deleteDayLogUseCase   : DeleteDayLogUseCase,
    private val deleteMyProfileUseCase: DeleteMyProfileUseCase,
    private val eventHandler          : EventHandler
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Uninitialized)
    val loginUiState = _loginState.asStateFlow()

    private val _myProfileUiState: MutableStateFlow<User?> = MutableStateFlow(null)
    val myProfileUiState = _myProfileUiState.asStateFlow()

    private val _motionProgress = MutableStateFlow(0f)
    val motionProgress = _motionProgress.asStateFlow()

    private fun showLoading() { _isLoading.value = true }
    private fun hideLoading() { _isLoading.value = false }

    init {
        initLoginStateWithMyProfile()
        handleEvent()
    }

    private fun initLoginStateWithMyProfile() {
        viewModelScope.launch {
            getMyProfileUseCase().collect { myProfile ->
                _loginState.value = myProfile?.let {
                    LoginState.LoggedIn
                } ?: LoginState.NotLoggedIn

                _myProfileUiState.value = myProfile?.mapToPresenter()
            }
        }
    }

    private fun handleEvent() {
        viewModelScope.launch {
            eventHandler.event.collect { event ->
                when (event) {
                    is Event.RequestUpdateBookmark -> updateBookmark(event.placeName)
                    is Event.RequestDeleteDayLog -> deleteDayLog(event.dayLogId)
                    else -> {}
                }
            }
        }
    }

    fun updateBookmark(placeName: String) {
        viewModelScope.launch {
            if (_myProfileUiState.value == null) {
                eventHandler.emitEvent(Event.ShowLoginDialogEvent)
                return@launch
            }

            updateBookmarkUseCase(placeName)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> showLoading()
                        is ApiResult.Success -> hideLoading()
                        is ApiResult.Error -> {
                            hideLoading()
                            Logger.e(apiResult.throwable.localizedMessage!!)

                            eventHandler.emitEvent(Event.ApiError("occur Error [Update Bookmark API]"))
                        }
                    }
                }
        }
    }

    fun deleteDayLog(dayLogId: Int) {
        viewModelScope.launch {
            deleteDayLogUseCase(dayLogId)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> showLoading()
                        is ApiResult.Success -> hideLoading()
                        is ApiResult.Error -> {
                            hideLoading()
                            Logger.e(apiResult.throwable.localizedMessage!!)

                            eventHandler.emitEvent(Event.ApiError("occur Error [Delete DayLog API]"))
                        }
                    }
                }
        }
    }

    fun requestLogOut() {
        viewModelScope.launch {
            deleteMyProfileUseCase()
        }
    }

    fun updateMyProfile(profileItem: User) {
        _myProfileUiState.value = profileItem
    }

    fun saveMotionProgress(value: Float) {
        _motionProgress.value = value
    }
}

sealed class LoginState {
    object Uninitialized : LoginState()
    object NotLoggedIn   : LoginState()
    object LoggedIn      : LoginState()
}