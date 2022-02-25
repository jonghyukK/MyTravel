package org.kjh.mytravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetPlaceRankingUseCase
import com.example.domain2.usecase.GetRecentPostsUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: HomeViewModel
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */

data class PlaceRankingUiState(
    val placeRankingItems: List<PlaceWithRanking> = listOf()
)

data class RecentPostsUiState(
    val recentPlaceItems: PagingData<Post>? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getRecentPostsUseCase: GetRecentPostsUseCase,
    val getPlaceRankingUseCase: GetPlaceRankingUseCase,
    val loginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {

    private val _recentPostsUiState = MutableStateFlow(RecentPostsUiState())
    val recentPostsUiState: StateFlow<RecentPostsUiState> = _recentPostsUiState

    private val _placeRankingUiState = MutableStateFlow(PlaceRankingUiState())
    val placeRankingUiState: StateFlow<PlaceRankingUiState> = _placeRankingUiState

    init {
        viewModelScope.launch {
            getRecentPostsUseCase(loginPreferenceUseCase().email)
                .map { pagingData ->
                    pagingData.map { it.mapToPresenter() }
                }
                .cachedIn(viewModelScope)
                .collectLatest { result ->
                    _recentPostsUiState.update {
                        it.copy(recentPlaceItems = result)
                    }
                }
        }

        viewModelScope.launch {
            getPlaceRankingUseCase()
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _placeRankingUiState.update { currentUiState ->
                                currentUiState.copy(
                                    placeRankingItems = result.data.map { it.mapToPresenter() }
                                )
                            }
                        }
                    }
                }
        }
    }

    fun updateRecentPosts() {
        viewModelScope.launch {
            getRecentPostsUseCase(loginPreferenceUseCase().email)
                .map { pagingData ->
                    pagingData.map { it.mapToPresenter() }
                }
                .cachedIn(viewModelScope)
                .collectLatest { result ->
                    if (_recentPostsUiState.value.recentPlaceItems != result) {
                        Logger.e("Added New Post")
                    }

                    _recentPostsUiState.update {
                        it.copy(recentPlaceItems = result)
                    }
                }
        }
    }
}