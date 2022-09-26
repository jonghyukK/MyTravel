package org.kjh.mytravel.ui.features.home.ranking

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.LayoutHomeSectionHeaderBinding
import org.kjh.mytravel.databinding.VhPlaceRankingRowBinding
import org.kjh.mytravel.model.PlaceRankingItemUiState
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener

/**
 * MyTravel
 * Class: HomeAdapter
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */

class PlaceRankingHorizontalWrapAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val childViewState = mutableMapOf<Int, Parcelable?>()
    private val rankingUiItems = mutableListOf<RankingItemModel>()

    fun setRankingItems(items: List<PlaceRankingItemUiState>) {
        val addedHeaderLists = listOf(
            RankingItemModel.HeaderItem,
            RankingItemModel.RankingItems(items)
        )

        if (rankingUiItems.isEmpty()) {
            rankingUiItems.addAll(addedHeaderLists)
            notifyItemRangeInserted(0, 2)
        } else {
            rankingUiItems.clear()
            rankingUiItems.addAll(addedHeaderLists)
            notifyItemChanged(1)
        }
    }

    override fun getItemViewType(position: Int) = when (rankingUiItems[position]) {
        is RankingItemModel.HeaderItem -> R.layout.layout_home_section_header
        is RankingItemModel.RankingItems -> R.layout.vh_place_ranking_row
        else -> throw Exception("Unknown View")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        R.layout.layout_home_section_header ->
            HeaderViewHolder(
                LayoutHomeSectionHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        else -> PlaceRankingRowViewHolder(
            VhPlaceRankingRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = rankingUiItems[position]
        if (item is RankingItemModel.HeaderItem) {
            (holder as HeaderViewHolder).bind("이번 주 가장 핫한 장소 BEST 7")
        } else if (item is RankingItemModel.RankingItems) {
            (holder as PlaceRankingRowViewHolder).bind(item.items)
        }
    }

    override fun getItemCount() = rankingUiItems.size

    class HeaderViewHolder(
        val binding: LayoutHomeSectionHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {
            binding.headerTitle = title
        }
    }

    inner class PlaceRankingRowViewHolder(
        val binding: VhPlaceRankingRowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()
        private val placeRankingAdapter = PlaceRankingListAdapter()

        init {
            binding.rvRankingRecyclerView.apply {
                setHasFixedSize(true)
                adapter = placeRankingAdapter
                snapHelper.attachToRecyclerView(this)
                addItemDecoration(PlaceRankingItemDecoration())
                addOnItemTouchListener(OnNestedHorizontalTouchListener())
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object : OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.rvRankingRecyclerView.layoutManager?.onSaveInstanceState()
                            }
                        }
                    )
                )
            }
        }

        fun bind(items: List<PlaceRankingItemUiState>) {
            placeRankingAdapter.submitList(items)
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.rvRankingRecyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvRankingRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }

    companion object {
        sealed class RankingItemModel {
            object HeaderItem: RankingItemModel()
            data class RankingItems(val items: List<PlaceRankingItemUiState>): RankingItemModel()
        }
    }
}

