package org.kjh.mytravel.ui.features.home

import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.kjh.mytravel.model.BannerItemUiState
import org.kjh.mytravel.model.PlaceRankingItemUiState
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.banner.BannerHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter

/**
 * MyTravel
 * Class: HomeBindingAdapters
 * Created by jonghyukkang on 2022/07/19.
 *
 * Description:
 */


@BindingAdapter("addOnScrollListener")
fun bindOnScrollListenerForHomeBanners(view: RecyclerView, value: Boolean) {
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        val lm = view.layoutManager as LinearLayoutManager

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val itemCount = lm.itemCount / 2
            val firstItemVisible = lm.findFirstVisibleItemPosition()

            if (itemCount != 0) {
                if (firstItemVisible != 1 && (firstItemVisible % itemCount == 1)) {
                    lm.scrollToPosition(1)
                } else if (firstItemVisible == 0) {
                    lm.scrollToPositionWithOffset(
                        itemCount,
                        -view.computeHorizontalScrollOffset()
                    )
                }
            }
        }
    })
}

@BindingAdapter("refreshAction")
fun SwipeRefreshLayout.bindOnRefreshListener(refreshAction: () -> Unit) {
    setOnRefreshListener {
        refreshAction()
        isRefreshing = false
    }
}

@BindingAdapter(
    "bannersUiState",
    "rankingsUiState"
)
fun RecyclerView.bindConcatAdapterToHomeRecyclerView(
    bannersUiState  : UiState<List<BannerItemUiState>>,
    rankingsUiState : UiState<List<PlaceRankingItemUiState>>,
) {
    adapter.let {
        val adapters = (it as ConcatAdapter).adapters
        adapters.forEach { childAdapter ->
            when (childAdapter) {
                is BannerHorizontalWrapAdapter -> when (bannersUiState) {
                    is UiState.Init,
                    is UiState.Loading -> {}
                    is UiState.Success -> childAdapter.setBannerItems(bannersUiState.data)
                    is UiState.Error -> {
                        Toast.makeText(context, bannersUiState.errorMsg, Toast.LENGTH_SHORT).show()
                        bannersUiState.errorAction?.invoke()
                    }
                }

                is PlaceRankingHorizontalWrapAdapter -> when (rankingsUiState) {
                    is UiState.Init,
                    is UiState.Loading -> {}
                    is UiState.Success -> childAdapter.setRankingItems(rankingsUiState.data)
                    is UiState.Error -> {
                        Toast.makeText(context, rankingsUiState.errorMsg, Toast.LENGTH_SHORT).show()
                        rankingsUiState.errorAction?.invoke()
                    }
                }
            }
        }
    }
}