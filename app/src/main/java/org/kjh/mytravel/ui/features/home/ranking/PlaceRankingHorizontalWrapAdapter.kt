package org.kjh.mytravel.ui.features.home.ranking

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
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

class PlaceRankingHorizontalWrapAdapter(
    private val rankingListAdapter: PlaceRankingListAdapter
) : RecyclerView.Adapter<PlaceRankingHorizontalWrapAdapter.PlaceRankingRowViewHolder>() {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceRankingRowViewHolder(
            VhPlaceRankingRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), rankingListAdapter, childViewState
        )

    override fun onBindViewHolder(holder: PlaceRankingRowViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    class PlaceRankingRowViewHolder(
        private val binding: VhPlaceRankingRowBinding,
        private val rankingListAdapter: PlaceRankingListAdapter,
        private val childViewState: MutableMap<Int, Parcelable?>
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()

        init {
            binding.rvRankingRecyclerView.apply {
                setHasFixedSize(true)
                adapter = rankingListAdapter
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

        fun bind() {
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
}

