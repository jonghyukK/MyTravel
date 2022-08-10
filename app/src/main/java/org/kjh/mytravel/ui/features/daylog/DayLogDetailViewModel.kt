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
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlaceUseCase
import org.kjh.domain.usecase.GetPlaceWithAroundUseCase
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler

/**
 * MyTravel
 * Class: PlaceDetailViewModel
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */

sealed class AroundPlaceUiModel {
    data class PlaceItem(val place: Place): AroundPlaceUiModel()
    data class HeaderItem(val headerTitle: String): AroundPlaceUiModel()
}

data class SelectablePost(
    val postItem  : Post,
    val isSelected: Boolean = false
)

class DayLogDetailViewModel @AssistedInject constructor(
    private val getPlaceWithAroundUseCase: GetPlaceWithAroundUseCase,
    private val getPlaceUseCase: GetPlaceUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    @Assisted private val initPlaceName: String,
    @Assisted private val initPostId: Int
): ViewModel() {

    data class PlaceDetailUiState(
        val isLoading      : Boolean = false,
        val wholePostItems : List<SelectablePost> = listOf(),
        val currentPostItem: Post? = null
    )

    private val _uiState = MutableStateFlow(PlaceDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _isCollapsed = MutableStateFlow(false)
    val isCollapsed = _isCollapsed.asStateFlow()

    val aroundPlaceItemsPagingData = getPlaceWithAroundUseCase(initPlaceName)
        .map { pagingData ->
            pagingData.map {
                AroundPlaceUiModel.PlaceItem(it.mapToPresenter())
            }.filter { it.place.placeName != initPlaceName }
        }
        .map {
            it.insertSeparators { before, after ->
                if (after == null) {
                    return@insertSeparators null
                }

                if (before == null) {
                    AroundPlaceUiModel.HeaderItem("주변 가볼만한 장소")
                } else {
                    null
                }
            }
        }
        .cachedIn(viewModelScope)

    init {
        fetchPlaceByPlaceName()
    }

    private fun fetchPlaceByPlaceName() {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { apiResult ->
                   when (apiResult) {
                       is ApiResult.Loading -> _uiState.update {
                           it.copy(isLoading = true)
                       }

                       is ApiResult.Success -> {
                           val result = apiResult.data.mapToPresenter()

                           _uiState.update {
                               it.copy(
                                   isLoading       = false,
                                   wholePostItems  = makeSelectablePostItems(result.posts),
                                   currentPostItem = makeInitSelectedPostItem(result.posts)
                               )
                           }
                       }

                       is ApiResult.Error -> {
                           val errorMsg = "occur Error [Fetch PlaceByPlaceName API"
                           globalErrorHandler.sendError(errorMsg)
                           _uiState.update {
                               it.copy(isLoading = false)
                           }
                       }
                   }
                }
        }
    }

    private fun makeSelectablePostItems(posts: List<Post>) =
        posts.mapIndexed { index, post ->
            SelectablePost(
                postItem   = post,
                isSelected = if (initPostId == -1) index == 0 else initPostId == post.postId
            )
        }

    private fun makeInitSelectedPostItem(posts: List<Post>) =
        if (initPostId == -1)
            posts[0]
        else
            posts.find { it.postId == initPostId }

    fun changeCurrentPostItem(selectedItem: Post) {
        val updateSelectStatePosts = _uiState.value.wholePostItems.map { prevPost ->
            prevPost.copy(
                isSelected = prevPost.postItem.postId == selectedItem.postId
            )
        }

        _uiState.update {
            it.copy(
                wholePostItems = updateSelectStatePosts,
                currentPostItem = selectedItem
            )
        }
    }

    val updateCollapsed = fun(value: Boolean) {
        _isCollapsed.value = value
    }

    @AssistedFactory
    interface DayLogDetailAssistedFactory {
        fun create(placeName: String, postId: Int): DayLogDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DayLogDetailAssistedFactory,
            placeName: String,
            postId: Int
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(placeName, postId) as T
            }
        }
    }
}