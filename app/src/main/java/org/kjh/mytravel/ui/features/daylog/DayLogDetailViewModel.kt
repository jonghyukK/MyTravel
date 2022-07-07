package org.kjh.mytravel.ui.features.daylog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlaceUseCase
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

data class SelectablePost(
    val postItem  : Post,
    val isSelected: Boolean = false
)

class DayLogDetailViewModel @AssistedInject constructor(
    private val getPlaceUseCase: GetPlaceUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    @Assisted private val initPlaceName: String,
    @Assisted private val initPostId: Int
): ViewModel() {

    data class PlaceDetailUiState(
        val wholePostItems : List<SelectablePost> = listOf(),
        val currentPostItem: Post? = null
    )

    private val _placeDetailUiState = MutableStateFlow(PlaceDetailUiState())
    val placeDetailUiState = _placeDetailUiState.asStateFlow()

    init {
        fetchPlaceDetailByPlaceName()
    }

    private fun fetchPlaceDetailByPlaceName() {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { apiResult ->
                   when (apiResult) {
                       is ApiResult.Loading -> {}
                       is ApiResult.Success -> {
                           val placeItem = apiResult.data.mapToPresenter()

                           _placeDetailUiState.update {
                               it.copy(
                                   wholePostItems = makeSelectablePostItems(placeItem.posts),
                                   currentPostItem = makeInitSelectedPostItem(placeItem.posts)
                               )
                           }
                       }
                       is ApiResult.Error -> {}
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

    fun changeCurrentPostItem(item: Post) {
        val updateSelectStatePosts = _placeDetailUiState.value.wholePostItems.map { prevPost ->
            prevPost.copy(
                isSelected = prevPost.postItem.postId == item.postId
            )
        }

        _placeDetailUiState.update {
            it.copy(
                wholePostItems = updateSelectStatePosts,
                currentPostItem = item
            )
        }
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