package org.kjh.mytravel.ui.features.home.ranking

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceRankingRowBinding
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener

/**
 * MyTravel
 * Class: HomeAdapter
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
class PlaceRankingHorizontalWrapAdapter(
    private val placeRankingAdapter: PlaceRankingListAdapter
): RecyclerView.Adapter<PlaceRankingHorizontalWrapAdapter.PlaceRankingRowViewHolder>() {

    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceRankingRowViewHolder(
            VhPlaceRankingRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceRankingRowViewHolder, position: Int) {
        holder.bind()
    }

    override fun onViewRecycled(holder: PlaceRankingRowViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        childViewState[key] = holder.binding.rvPlaceRankingListConcat.layoutManager?.onSaveInstanceState()
    }

    override fun getItemCount() = 1

    inner class PlaceRankingRowViewHolder(
        val binding: VhPlaceRankingRowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()

        init {
            binding.rvPlaceRankingListConcat.apply {
                setHasFixedSize(true)
                snapHelper.attachToRecyclerView(this)
                addItemDecoration(PlaceRankingItemDecoration(this.context, 20, 20, 0,0))
                adapter = placeRankingAdapter
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object : OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.rvPlaceRankingListConcat.layoutManager?.onSaveInstanceState()
                            }
                        }
                    ))
            }
        }

        fun bind() {
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.rvPlaceRankingListConcat.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvPlaceRankingListConcat.layoutManager?.scrollToPosition(0)
            }
        }
    }
}

