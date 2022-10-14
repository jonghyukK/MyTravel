package org.kjh.mytravel.ui.features.daylog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetMyProfileUseCase
import org.kjh.domain.usecase.GetPlaceUseCase
import org.kjh.domain.usecase.GetPlaceWithAroundUseCase
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.model.common.PagingWithHeaderUiModel

/**
 * MyTravel
 * Class: PlaceDetailViewModel
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */

class DayLogDetailViewModel @AssistedInject constructor(
    private val getPlaceWithAroundUseCase: GetPlaceWithAroundUseCase,
    private val getPlaceUseCase         : GetPlaceUseCase,
    private val getMyProfileUseCase     : GetMyProfileUseCase,
    private val eventHandler            : EventHandler,
    @Assisted private val initPlaceName : String,
    @Assisted private val initDayLogId  : Int
): ViewModel() {

    private val _uiState = MutableStateFlow(DayLogDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _isCollapsed = MutableStateFlow(false)
    val isCollapsed = _isCollapsed.asStateFlow()

    val aroundPlaceItemsPagingData: Flow<PagingData<PagingWithHeaderUiModel<Place>>>

    init {
        fetchPlaceByPlaceName()
        updateBookmarkState()

        aroundPlaceItemsPagingData =
            getPlaceWithAroundUseCase(initPlaceName)
                .map { pagingData ->
                    pagingData
                        .filter { it.placeName != initPlaceName }
                        .map { PagingWithHeaderUiModel.PagingItem(it.mapToPresenter()) }
                        .insertSeparators { before, after ->
                            if (after == null) {
                                return@insertSeparators null
                            }
                            when (before) {
                                null -> PagingWithHeaderUiModel.HeaderItem
                                else -> null
                            }
                        }
                }
                .cachedIn(viewModelScope)
    }

    private fun fetchPlaceByPlaceName() {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { apiResult ->
                   when (apiResult) {
                       is ApiResult.Loading -> isLoading(true)

                       is ApiResult.Success -> {
                           val result = apiResult.data.mapToPresenter()
                           val dayLogs = result.dayLogs

                           _uiState.update { currentUiState ->
                               currentUiState.copy(
                                   isLoading         = false,
                                   wholeDayLogItems  = dayLogs,
                                   currentDayLogItem = dayLogs.findDayLogByDayLogId(initDayLogId),
                                   profileItems      = dayLogs.mapToDayLogProfileItems(
                                       initDayLogId = initDayLogId,
                                       onChangeCurrentDayLog = { changeCurrentDayLogItem(it) }
                                   ),
                                   onBookmark = { sendEventForUpdateBookmark(initPlaceName) }
                               )
                           }
                       }

                       is ApiResult.Error -> {
                           isLoading(false)
                           eventHandler.emitEvent(
                               Event.ApiError("occur Error [Fetch PlaceByPlaceName API]"))
                       }
                   }
                }
        }
    }

    private fun updateBookmarkState() {
        viewModelScope.launch {
            getMyProfileUseCase()
                .map { it?.bookMarks }
                .distinctUntilChanged()
                .collect { bookmarks ->
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            isBookmarked = bookmarks?.any { it.placeName == initPlaceName } ?: false
                        )
                    }
                }
        }
    }

    private fun sendEventForUpdateBookmark(placeName: String) {
        viewModelScope.launch {
            eventHandler.emitEvent(Event.RequestUpdateBookmark(placeName))
        }
    }

    private fun changeCurrentDayLogItem(selectedDayLogId: Int) {
        _uiState.getAndUpdate { currentUiState ->
            with (currentUiState) {
                copy(
                    currentDayLogItem = wholeDayLogItems.findDayLogByDayLogId(selectedDayLogId),
                    profileItems      = profileItems.updateSelected(selectedDayLogId)
                )
            }
        }
    }

    private fun isLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    val updateCollapsed = fun(value: Boolean) {
        _isCollapsed.value = value
    }

    @AssistedFactory
    interface DayLogDetailAssistedFactory {
        fun create(placeName: String, dayLogId: Int): DayLogDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DayLogDetailAssistedFactory,
            initPlaceName: String,
            initDayLogId: Int
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(initPlaceName, initDayLogId) as T
            }
        }
    }
}

data class DayLogProfileItemUiState(
    val profileImg  : String? = null,
    val nickName    : String = "",
    val email       : String = "",
    val dayLogId    : Int = -1,
    val isSelected  : Boolean = false,
    val onChangeSelected : () -> Unit
)

data class DayLogDetailUiState(
    val isLoading        : Boolean = false,
    val currentDayLogItem: DayLog? = null,
    val wholeDayLogItems : List<DayLog> = emptyList(),
    val profileItems     : List<DayLogProfileItemUiState> = emptyList(),
    val isBookmarked     : Boolean = false,
    val onBookmark       : () -> Unit = {}
)

private fun List<DayLogProfileItemUiState>.updateSelected(selectedDayLogId: Int) =
    this.map {
        it.copy(isSelected = it.dayLogId == selectedDayLogId)
    }

private fun List<DayLog>.findDayLogByDayLogId(dayLogId: Int) =
    if (dayLogId == -1)
        first()
    else
        find { it.dayLogId == dayLogId}


private fun List<DayLog>.mapToDayLogProfileItems(
    initDayLogId         : Int,
    onChangeCurrentDayLog: (Int) -> Unit
) = mapIndexed { index, dayLog ->
        DayLogProfileItemUiState(
            profileImg  = dayLog.profileImg,
            nickName    = dayLog.nickName,
            email       = dayLog.email,
            dayLogId    = dayLog.dayLogId,
            isSelected  = (dayLog.dayLogId == initDayLogId) || (index == 0 && initDayLogId == -1),
            onChangeSelected = { onChangeCurrentDayLog(dayLog.dayLogId) }
        )
    }